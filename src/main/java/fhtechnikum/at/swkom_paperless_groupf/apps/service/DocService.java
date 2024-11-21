package fhtechnikum.at.swkom_paperless_groupf.apps.service;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import fhtechnikum.at.swkom_paperless_groupf.apps.repository.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DocService {

    @Autowired
    private DocRepository docRepository;

    public Doc saveDocument(MultipartFile fileToSave) throws IOException {
        Doc doc = new Doc(fileToSave.getOriginalFilename());
        Doc savedDoc = docRepository.save(doc);

        String fileName = savedDoc.getId().toString() + ".pdf";
        String storagePath = "/data/pdf_storage/";
        Path path = Paths.get(storagePath, fileName);

        Files.write(path, fileToSave.getBytes());

        // TODO: fix the return type, weil wtf soll das sein
        return savedDoc;
    }

    public List<Doc> findAll() {
        return docRepository.findAll();
    }

}
