package com.yurima.letsplay.dto;

import lombok.Data;

@Data
public class GameView {
    private String id;
    private String name;
    private String studio;
    private Short year;
    private String genre;
    private Byte rating;
    private boolean played;
    private String description;
    private boolean masterpiece;
}
