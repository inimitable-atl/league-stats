package com.inimitable.mapper;

import com.inimitable.model.Participant;
import com.sethtomy.match.dto.ParticipantDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    Participant fromDTO(ParticipantDTO participantDTO);
}