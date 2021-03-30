package kvinz.roflanchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "reset_token")
@ToString(exclude = "users")
public class ResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reset_token_id")
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.LAZY, optional = false,cascade = {CascadeType.MERGE,CascadeType.DETACH})
    @JoinColumn(name = "user_id", nullable = false)
    private User users;

    private Date expiryDate;

}
