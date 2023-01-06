package com.yurima.letsplay.service;

import com.yurima.letsplay.model.GameModel;
import com.yurima.letsplay.dto.GameView;
import com.yurima.letsplay.dto.OperationReport;
import com.yurima.letsplay.file.FileLoader;
import com.yurima.letsplay.mapper.GameMapper;
import com.yurima.letsplay.repo.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository repository;
    private final GameMapper mapper;
    private final FileLoader fileLoader;

    public List<GameView> getGames() {
        List<GameModel> games = repository.findAll();
        return mapper.fromModels(games);
    }

    public GameView getGame(String id) {
        Optional<GameModel> byId = repository.findById(id);
        GameModel model = byId.orElseThrow(RuntimeException::new);
        return mapper.fromModel(model);
    }

    public GameView saveGame(GameView gameView) {
        GameModel entity = mapper.toModel(gameView);
        repository.save(entity);
        gameView.setId(entity.getId());
        return gameView;
    }

    public void updateGame(GameView gameView, String id) {
        GameModel entity = mapper.toModel(gameView);
        entity.setId(id);
        repository.save(entity);
    }

    public OperationReport importFile(MultipartFile file) throws IOException {
        List<GameModel> games = fileLoader.fromXls(file);
        return trySave(games);
    }

    public byte[] exportFile() throws IOException {
        Collection<GameModel> games = repository.findAll();
        return fileLoader.toXls(games);
    }
    private OperationReport trySave(List<GameModel> games) {
        OperationReport report = new OperationReport();
        games.forEach(game -> {
            try {
                repository.save(game);
                report.success();
            } catch (DataIntegrityViolationException e) {
                log.error("Not able to save entity with name {}", game.getName());
                report.error();
            }
        });
        return report;
    }

    public void deleteGame(String id) {
        repository.deleteById(id);
    }

    public GameView getRandom() {
        final long count = repository.count();
        final long random = ThreadLocalRandom.current().nextLong(count);
        return repository.getByOffset(random)
                .map(mapper::fromModel)
                .orElse(null);

    }
}
