package kvinz.roflanchat.service;

import kvinz.roflanchat.controller.error_handler.custom_errors.BadRequestException;
import kvinz.roflanchat.domain.GeneralChat;
import kvinz.roflanchat.domain.User;
import kvinz.roflanchat.mapper.GeneralChatMapper;
import kvinz.roflanchat.model.ChatMessage;
import kvinz.roflanchat.model.GeneralChatDTO;
import kvinz.roflanchat.model.GeneralChatListDTO;
import kvinz.roflanchat.repository.GeneralChatRepository;
import kvinz.roflanchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    GeneralChatRepository chatRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveChatLogs(ChatMessage chatMessage) {
        User user = userRepository.findByEmail(chatMessage.getFrom());
        if (user == null) {
            throw new BadRequestException("No user with this email");
        }
        GeneralChat generalChat = new GeneralChat(user,chatMessage.getMessage(),new Date());

        chatRepository.save(generalChat);
    }

    @Override
    public GeneralChatListDTO getAllLogs(Pageable paging) {
        GeneralChatListDTO generalChatListDTO = new GeneralChatListDTO();
        List<GeneralChatDTO> generalChats =
                GeneralChatMapper.INSTANCE.toGeneralChatDTOList(chatRepository.findAll(paging).getContent());
        generalChatListDTO.setGeneralChatList(generalChats);
        if(chatRepository.findAll(paging).hasNext()){
            generalChatListDTO.setNext("https://roflan-chat-backend.herokuapp.com/getLogs?page_number="
                    + (chatRepository.findAll(paging).getNumber()+1));
        }
        return generalChatListDTO;
    }
}
