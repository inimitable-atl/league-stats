package com.inimitable.mapper;

import com.inimitable.model.Summoner;
import com.sethtomy.summoner.SummonerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SummonerMapper {
    Summoner fromDTO(SummonerDTO summonerDTO);
}
