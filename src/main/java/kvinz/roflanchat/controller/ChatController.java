package kvinz.roflanchat.controller;

import kvinz.roflanchat.domain.GeneralChat;
import kvinz.roflanchat.model.ChatMessage;
import kvinz.roflanchat.model.GeneralChatListDTO;
import kvinz.roflanchat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping("/getLogs")
    public GeneralChatListDTO getAllLogs(@RequestParam(defaultValue = "0",name = "page_number") Integer pageNo,
                                         @RequestParam(defaultValue = "20",name = "page_size") Integer pageSize){
        Pageable paging = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC,"sendingDate");
        return chatService.getAllLogs(paging);
    }


    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage messageProcessing(@Payload ChatMessage chatMessage) {
        chatService.saveChatLogs(chatMessage);
        return chatMessage;
    }

}
