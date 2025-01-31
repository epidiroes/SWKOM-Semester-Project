package fhtechnikum.at.swkom_paperless_groupf.apps.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "t_documents")
@Slf4j
public class Doc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 50, message = "Title can not be empty! Must be between 2 and 50 characters long.")
    private String title;

    @NotNull
    private LocalDateTime uploadDate;

    @PrePersist
    public void setUploadDate(){
        this.uploadDate = LocalDateTime.now();
    }

    @NotNull
    private byte[] fileContent;

    public Doc(String title, byte[] fileContent) {
        this.title = title;
        this.fileContent = fileContent;
        log.debug("Doc entity created: title='{}', fileContentLength={}", title, fileContent.length);
    }

}
