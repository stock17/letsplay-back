package com.yurima.letsplay.mapper;

import com.yurima.letsplay.model.GameModel;
import com.yurima.letsplay.dto.GameView;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Named("fromModel")
    GameView fromModel (GameModel source);

    @Named("toModel")
    @InheritInverseConfiguration
    GameModel toModel (GameView source);

    @IterableMapping(qualifiedByName = "fromModel")
    List<GameView> fromModels (Iterable<GameModel> sources);

    @IterableMapping(qualifiedByName = "toModel")
    List<GameModel> toModels (List<GameView> sources);
}
