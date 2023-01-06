package com.yurima.letsplay.controller;

import com.yurima.letsplay.dto.GameView;
import com.yurima.letsplay.dto.OperationReport;
import com.yurima.letsplay.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public List<GameView> getAll() {
        return gameService.getGames();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameView getById(@PathVariable String id) {
        return gameService.getGame(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameView save(@RequestBody GameView gameView) {
        return gameService.saveGame(gameView);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody GameView gameView, @PathVariable String id) {
        gameService.updateGame(gameView, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        gameService.deleteGame(id);
    }

    @PostMapping("/import")
    public OperationReport importFromFile(@RequestBody MultipartFile file) throws IOException {
        return gameService.importFile(file);
    }

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportToFile() throws IOException {
        byte[] bytes = gameService.exportFile();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(bytes);
    }

    @GetMapping(value = "/random")
    public GameView getRandom() {
        return gameService.getRandom();
    }
}
