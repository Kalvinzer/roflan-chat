package kvinz.roflanchat.model;

import kvinz.roflanchat.domain.GeneralChat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralChatListDTO {
    private List<GeneralChatDTO> generalChatList;
    private String next;
}
