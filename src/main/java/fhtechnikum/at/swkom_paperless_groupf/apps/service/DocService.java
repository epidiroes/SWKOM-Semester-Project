package fhtechnikum.at.swkom_paperless_groupf.apps.service;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import fhtechnikum.at.swkom_paperless_groupf.apps.repository.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DocService {

    @Autowired
    private DocRepository docRepository;

    public Doc saveDocument(MultipartFile fileToSave) throws IOException {
        Doc doc = new Doc(fileToSave.getOriginalFilename(), fileToSave.getBytes());
        Doc savedDoc = docRepository.save(doc);

        // TODO: fix the return type, weil wtf soll das sein
        return savedDoc;
    }

    public List<Doc> findAll() {
        return docRepository.findAll();
    }

}
