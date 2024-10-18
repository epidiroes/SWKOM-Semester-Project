package fhtechnikum.at.swkom_paperless_groupf.apps.controller;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import fhtechnikum.at.swkom_paperless_groupf.apps.repository.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/docs")
@CrossOrigin

public class DocController {
    @Autowired
    private DocRepository docRepository;

    @PostMapping
    public ResponseEntity<Doc> createDoc(@RequestBody Doc doc) {
        doc = docRepository.save(doc);

        return new ResponseEntity<>(doc, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Doc>> getDocs(){
        if(docRepository.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(docRepository.findAll(), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doc> getDoc(@PathVariable Long id) {
        if(docRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(docRepository.findById(id).get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public Doc updateDoc(@RequestBody Doc doc, @PathVariable Long id) {
        doc.setId(id);
        return docRepository.save(doc);
    }

    @DeleteMapping("/{id}")
    public void deleteDoc(@PathVariable Long id) {
        docRepository.deleteById(id);
    }
}
