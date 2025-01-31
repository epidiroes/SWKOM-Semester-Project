package fhtechnikum.at.swkom_paperless_groupf.apps.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElasticDoc {
    private String documentId;
    private String ocrText;
    private String filename;
    private String filetype;
    private long filesize;
    private boolean ocrJobDone;
    private LocalDateTime uploadDate;

    @JsonProperty("@timestamp")
    private String timestamp;
}