package fhtechnikum.at.swkom_paperless_groupf.apps.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.nio.file.Paths;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "t_documents")
public class Doc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 50, message = "Title can not be empty! Must be between 2 and 50 characters long.")
    private String title;

    @NotNull
    @CreatedDate
    private LocalDateTime uploadDate;

    @PrePersist
    public void generateFilePath() {
        // Erzeuge den Dateipfad im pdf_storage Volume
        String generatedFilePath = Paths.get("/data/pdf_storage", id.toString() + ".pdf").toString();

        // Setze den generierten Dateipfad in das Entity
        this.filePath = generatedFilePath;
    }

    @NotNull
    private String filePath;

    public Doc(String title) {}

}
