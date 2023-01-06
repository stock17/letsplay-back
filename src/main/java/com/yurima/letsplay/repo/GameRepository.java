package com.yurima.letsplay.repo;


import com.yurima.letsplay.model.GameModel;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Primary
@Repository("MongoGameRepository")
public interface GameRepository extends MongoRepository<GameModel, String> {

    @Aggregation(pipeline = {
            "{ '$skip' : ?0 }",
            "{ '$limit' : 1 }"
    })
    Optional<GameModel> getByOffset(long offset);
}
