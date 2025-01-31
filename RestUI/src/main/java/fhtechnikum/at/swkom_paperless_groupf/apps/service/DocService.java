package fhtechnikum.at.swkom_paperless_groupf.apps.service;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import fhtechnikum.at.swkom_paperless_groupf.apps.entity.ElasticDoc;
import fhtechnikum.at.swkom_paperless_groupf.apps.repository.DocRepository;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.minio.*;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocService {

    private final DocRepository docRepository;
    private final MinioClient minioClient;
    private final ElasticSearchService elasticSearchService;

    @Value("${minio.bucket}")
    protected String BUCKET_NAME;

    @Autowired
    public DocService(DocRepository docRepository, MinioClient minioClient, ElasticSearchService elasticSearchService) {
        this.docRepository = docRepository;
        this.minioClient = minioClient;
        this.elasticSearchService = elasticSearchService;
    }

    public Doc saveDocument(MultipartFile fileToSave) throws Exception {
        log.info("Saving document: {}", fileToSave.getOriginalFilename());

        try {
            Doc doc = new Doc(fileToSave.getOriginalFilename(), fileToSave.getBytes());
            Doc savedDoc = docRepository.save(doc);
            log.info("Document successfully saved in DB with ID: {}", savedDoc.getId());

            String fileNameForMinio = savedDoc.getId().toString() + "_" + savedDoc.getTitle();

            // Check if the bucket exists, if not, create it
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
            }
            try {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(BUCKET_NAME)
                                .object(fileNameForMinio)
                                .stream(fileToSave.getInputStream(), fileToSave.getSize(), -1)
                                .contentType(fileToSave.getContentType())
                                .build());
            } catch (Exception e) {
                log.error("Failed to upload file to MinIO: {}", e.getMessage(), e);
                throw e;
            }
            log.info("Document successfully saved in minio with Name: {}", fileNameForMinio);

            return savedDoc;
        } catch (Exception e) {
            log.error("Error saving document: {}", fileToSave.getOriginalFilename(), e);
            throw e;
        }
    }

    public List<Doc> findAll() {
        log.info("Fetching all documents...");
        List<Doc> docs = docRepository.findAll();
        log.debug("Fetched {} documents from the repository.", docs.size());
        return docs;
    }

    public List<Doc> searchDocuments(String query) {
        log.info("Querying Elasticsearch with query: {}", query);

        // Elasticsearch - actual search
        List<ElasticDoc> elasticResults = elasticSearchService.searchDocuments(query);

        // TODO CHANGE COMMENTS + LOGS
        // log.info("Mapping Elasticsearch results to DTOs using database data.");

        // Retrieve document information from the database and map to DTOs
        return elasticResults.stream()
                .map(result -> {
                    try {
                        // Fetch the document from the database or throw error
                        return docRepository.findById(Long.valueOf(result.getDocumentId()))
                                .orElseThrow(() -> new RuntimeException("Document not found in database for ID: " + result.getDocumentId()));
                    } catch (Exception e) {
                        log.error("Error fetching document data for ID {}: {}", result.getDocumentId(), e.getMessage(), e);
                        // If an error occurs, return an empty DTO or other handling
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }


}
