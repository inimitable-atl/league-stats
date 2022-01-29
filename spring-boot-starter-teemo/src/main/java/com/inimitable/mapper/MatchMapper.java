package com.inimitable.mapper;

import com.inimitable.model.Match;
import com.sethtomy.match.dto.MatchDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ParticipantMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MatchMapper {
    @Mapping(target = "participants", source = "info.participants")
    @Mapping(target = ".", source = "metadata")
    @Mapping(target = ".", source = "info")
    Match fromDTO(MatchDTO matchDTO);
}