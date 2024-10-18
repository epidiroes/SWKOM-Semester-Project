package fhtechnikum.at.swkom_paperless_groupf.apps;


import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import fhtechnikum.at.swkom_paperless_groupf.apps.repository.DocRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DBInitialize {
    @Autowired
    private DocRepository docRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void handleApplicationReady(){
        docRepository.save(
                new Doc(
                        "Wie kann man Apfelkuchen am besten einfrieren?",
                        "Karli Huber",
                        2024
                )
        );
        docRepository.save(
                new Doc(
                        "Waschbären in der allgemeinen Bibliothek",
                        "Sieglinde Raimund",
                        1995

                )
        );
    }

}
