package fhtechnikum.at.swkom_paperless_groupf.apps.service;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import fhtechnikum.at.swkom_paperless_groupf.apps.repository.DocRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class DocService {

    @Autowired
    private DocRepository docRepository;

    public Doc saveDocument(MultipartFile fileToSave) throws IOException {
        log.info("Saving document: {}", fileToSave.getOriginalFilename());

        // old note before add logging: fix the return type, weil wtf soll das sein
        try {
            Doc doc = new Doc(fileToSave.getOriginalFilename(), fileToSave.getBytes());
            Doc savedDoc = docRepository.save(doc);
            log.info("Document saved successfully with ID: {}", savedDoc.getId());
            return savedDoc;
        } catch (IOException e) {
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

}
