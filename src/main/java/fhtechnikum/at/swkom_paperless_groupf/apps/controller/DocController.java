package fhtechnikum.at.swkom_paperless_groupf.apps.controller;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import fhtechnikum.at.swkom_paperless_groupf.apps.rabbitMQ.RabbitMQSender;
import fhtechnikum.at.swkom_paperless_groupf.apps.service.DocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/docs")
@CrossOrigin
@Slf4j
public class DocController {
    @Autowired
    DocService docService;

    @Autowired
    RabbitMQSender rabbitMQSender;

    @GetMapping
    public ResponseEntity<List<Doc>> getDocs(){
        log.info("Fetching all documents...");
        List<Doc> docs = docService.findAll();
        log.debug("Fetched {} documents.", docs.size());
        return new ResponseEntity<>(docs, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Doc> uploadDoc(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("Received a file upload request: {}", file.getOriginalFilename());

        if(file.isEmpty()){
            log.warn("File upload failed: Empty file.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // old note from before add logging: fix the return type, weil wtf soll das sein
        try {
            Doc savedDoc = docService.saveDocument(file);
            rabbitMQSender.sendMessage("File is uploaded to PaperlessDB");
            log.info("File successfully saved: {}", savedDoc.getId());
            return new ResponseEntity<>(savedDoc, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Error while saving file: {}", file.getOriginalFilename(), e);
            throw e;
        }
    }

}
