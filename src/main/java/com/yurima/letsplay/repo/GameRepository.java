package com.yurima.letsplay.repo;


import com.yurima.letsplay.model.GameModel;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository("MongoGameRepository")
public interface GameRepository extends MongoRepository<GameModel, String> {
}
