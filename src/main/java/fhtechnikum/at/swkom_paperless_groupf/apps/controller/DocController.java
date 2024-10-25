package fhtechnikum.at.swkom_paperless_groupf.apps.controller;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import fhtechnikum.at.swkom_paperless_groupf.apps.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docs")
@CrossOrigin

public class DocController {
    @Autowired
    DocService docService;

    @GetMapping
    public ResponseEntity<List<Doc>> getDocs(){

        return new ResponseEntity<>(docService.getDocs(), HttpStatus.OK);
    }

}
