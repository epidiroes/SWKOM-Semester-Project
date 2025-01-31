package fhtechnikum.at.ocrworker.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Slf4j
@Service
public class OCRService {

    @Autowired
    MinioClient minioClient;

    @Autowired
    private ElasticsearchServiceOCR elasticsearchServiceOCR;

    private final Tesseract tesseract;

    private final RabbitMQSenderOCR rabbitMQSenderOCR;

    public OCRService(RabbitMQSenderOCR rabbitMQSenderOCR) {
        this.rabbitMQSenderOCR = rabbitMQSenderOCR;
        tesseract = new Tesseract();
        tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata"); // Path to Tesseract data
        tesseract.setLanguage("eng");
        System.out.println("Tesseract OCR initialized with data path: /usr/share/tesseract-ocr/4.00/tessdata");
    }

    public String extractText(File inputFile) throws TesseractException {
        String text = tesseract.doOCR(inputFile);
        log.info("OCR extraction completed for file: {}", inputFile.getName());
        return text;
    }

    public void getFile(String fileName){
        try{
            log.info("Fetching document from Minio for document ID: {}", fileName);
            InputStream documentStream = minioClient.getObject(
                    GetObjectArgs.builder().bucket("documents").object(fileName).build());

            File tempFile = new File("/tmp/" + fileName + ".pdf");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = documentStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                log.info("File successfully downloaded to: {}", tempFile.getAbsolutePath());
            }catch (Exception e){
                log.error("Error occurred while downloading file: {}", fileName, e);
            }

            log.info("Starting OCR extraction for file: {}", fileName);

            String extractedText = extractText(tempFile);

            rabbitMQSenderOCR.sendMessageToRest(extractedText, fileName);

            try{
                String[] parts = fileName.split("_", 2);
                String id = parts[0];
                String name = parts[1];

                elasticsearchServiceOCR.indexDocument(id, name, extractedText);
            }catch (Exception e){
                log.error("Error occurred while indexing file with elasticsearch: {}", fileName, e);
            }


        }catch (Exception e){
            log.error("Error OCR getFile", e);
        }
    }
}
