package fhtechnikum.at.ocrworker.service;

import fhtechnikum.at.ocrworker.configuration.RabbitMQConfigOCR;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


@Service
@Slf4j
public class RabbitMQReceiverOCR {

    private final OCRService ocrService;

    public RabbitMQReceiverOCR(OCRService ocrService) {
        this.ocrService = ocrService;
        log.info("RabbitMQServiceOCR started");
    }

    @RabbitListener(queues = RabbitMQConfigOCR.OCR_QUEUE)
    public void receive(String message) {
        log.info("RabbitMQListener received message: {}", message);
        try{
            ocrService.getFile(message);

        }catch (Exception e){
            log.error("RabbitMQListener error", e);
        }
    }
}
