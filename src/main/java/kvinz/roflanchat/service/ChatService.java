package kvinz.roflanchat.service;

import kvinz.roflanchat.domain.GeneralChat;
import kvinz.roflanchat.model.ChatMessage;
import kvinz.roflanchat.model.GeneralChatListDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatService {
    void saveChatLogs(ChatMessage chatMessage);
    GeneralChatListDTO getAllLogs(Pageable paging);
}
