package fhtechnikum.at.swkom_paperless_groupf.apps.service;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocService {
    public List<Doc> getDocs() {
        List<Doc> docList = new ArrayList<>();
        docList.add(new Doc(
                "Wie kann man Apfelkuchen am besten einfrieren?",
                "Karli Huber",
                2024
        ));
        docList.add(new Doc(
                "Waschbären in der allgemeinen Bibliothek",
                "Sieglinde Raimund",
                1995
        ));
        return docList;
    }
}
