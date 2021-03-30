package kvinz.roflanchat.mapper;

import kvinz.roflanchat.domain.GeneralChat;
import kvinz.roflanchat.model.GeneralChatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GeneralChatMapper {

    GeneralChatMapper INSTANCE = Mappers.getMapper(GeneralChatMapper.class);

    GeneralChat toGeneralChat(GeneralChatDTO generalChatDTO);
    GeneralChatDTO toGeneralChatDTO(GeneralChat generalChat);
    List<GeneralChatDTO> toGeneralChatDTOList(List<GeneralChat> generalChats);
}
