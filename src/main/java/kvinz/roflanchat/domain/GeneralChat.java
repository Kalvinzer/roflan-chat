package kvinz.roflanchat.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "general_chat")
public class GeneralChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="general_chat_id")
    private Long id;
    @JoinColumn(name = "user_id")
    @OneToOne
    private User from;
    @Column(name = "message")
    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sending_date", columnDefinition = "timestamp with time zone not null")
    private Date sendingDate;

    public GeneralChat(User from, String message, Date sendingDate) {
        this.from = from;
        this.message = message;
        this.sendingDate = sendingDate;
    }
}
