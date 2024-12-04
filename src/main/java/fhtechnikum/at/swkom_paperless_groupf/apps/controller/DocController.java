package fhtechnikum.at.swkom_paperless_groupf.apps.controller;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import fhtechnikum.at.swkom_paperless_groupf.apps.rabbitMQ.RabbitMQSender;
import fhtechnikum.at.swkom_paperless_groupf.apps.service.DocService;
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

public class DocController {
    @Autowired
    DocService docService;

    @Autowired
    RabbitMQSender rabbitMQSender;

    @GetMapping
    public ResponseEntity<List<Doc>> getDocs(){

        return new ResponseEntity<>(docService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Doc> uploadDoc(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        rabbitMQSender.sendMessage("File is uploaded to PaperlessDB");

        // TODO: fix the return type, weil wtf soll das sein
        return new ResponseEntity<>(docService.saveDocument(file), HttpStatus.OK);
    }

}
