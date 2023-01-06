package com.yurima.letsplay.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "game")
public class GameModel {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String studio;
    private Short year;
    private String genre;
    private Byte rating;
    private boolean played;
    private String description;
    private boolean masterpiece;
}
