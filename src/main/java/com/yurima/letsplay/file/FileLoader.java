package com.yurima.letsplay.file;

import com.yurima.letsplay.model.GameModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.yurima.letsplay.file.Columns.*;

@Component
public class FileLoader {

    public List<GameModel> fromXls(MultipartFile file) throws IOException {

        List<GameModel> games = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> iterator = sheet.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            GameModel model = extractEntity(row);
            games.add(model);
        }

        return games;
    }

    private GameModel extractEntity(Row row) {
        String name = getString(row, NAME_COLUMN);
        String studio = getString(row, STUDIO_COLUMN);
        Integer year = getInt(row, YEAR_COLUMN);
        String genre = getString(row, GENRE_COLUMN);
        Integer rating = getInt(row, RATING_COLUMN);
        Boolean played = getBoolean(row, PLAYED_COLUMN);
        String description = getString(row, DESCRIPTION_COLUMN);
        Boolean masterpiece = getBoolean(row, MASTERPIECE_COLUMN);

        GameModel model = new GameModel();
        model.setName(name);
        model.setStudio(studio);
        if (year != null) {
            model.setYear(year.shortValue());
        }
        model.setGenre(genre);
        if (rating != null) {
            model.setRating(rating.byteValue());
        }
        model.setPlayed(Boolean.TRUE.equals(played));
        model.setDescription(description);
        model.setMasterpiece(Boolean.TRUE.equals(masterpiece));
        return model;
    }

    public byte[] toXls(Iterable<GameModel> games) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet();
            int rowCount = 0;
            for (GameModel game : games) {
                Row row = sheet.createRow(++rowCount);
                insertRecord(row, game);
            }
            workbook.write(out);
            return out.toByteArray();
        }
    }

    private void insertRecord(Row row, GameModel game) {
        Cell name = row.createCell(NAME_COLUMN);
        Cell studio = row.createCell(STUDIO_COLUMN);
        Cell year = row.createCell(YEAR_COLUMN);
        Cell genre = row.createCell(GENRE_COLUMN);
        Cell rating = row.createCell(RATING_COLUMN);
        Cell played = row.createCell(PLAYED_COLUMN);
        Cell description = row.createCell(DESCRIPTION_COLUMN);
        Cell masterpiece = row.createCell(MASTERPIECE_COLUMN);

        name.setCellValue(game.getName());
        studio.setCellValue(game.getStudio());
        if (game.getYear() != null) {
            year.setCellValue(game.getYear());
        }
        genre.setCellValue(game.getGenre());
        if (game.getRating() != null) {
            rating.setCellValue(game.getRating());
        }
        played.setCellValue(game.isPlayed());
        description.setCellValue(game.getDescription());
        masterpiece.setCellValue(game.isMasterpiece());
    }

    private String getString (Row row, int column) {
        return Optional.ofNullable(row.getCell(column))
                .map(Cell::getStringCellValue)
                .orElse(null);
    }

    private Integer getInt(Row row, int column) {
        return Optional.ofNullable(row.getCell(column))
                .map(Cell::getNumericCellValue)
                .map(Double::intValue)
                .orElse(null);
    }

    private Boolean getBoolean (Row row, int column) {
        return Optional.ofNullable(row.getCell(column))
                .map(Cell::getBooleanCellValue)
//                .map(Boolean::valueOf)
                .orElse(null);
    }
}
