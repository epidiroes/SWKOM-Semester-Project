package fhtechnikum.at.swkom_paperless_groupf.apps.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doc {
    private Long id;
    private String title;
    private String author;
    private int year;

    public Doc(String title, String author, int year) {
        this(null, title, author, year);
    }
}
