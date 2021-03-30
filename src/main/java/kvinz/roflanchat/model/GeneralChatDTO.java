package kvinz.roflanchat.model;

import kvinz.roflanchat.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralChatDTO {
    private Long id;
    private User from;
    private String message;
    private Date sendingDate;
}
