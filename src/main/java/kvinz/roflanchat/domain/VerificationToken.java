package kvinz.roflanchat.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import kvinz.roflanchat.service.TokenService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "verification_token")
@ToString(exclude = "users")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="token_id")
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.LAZY, optional = false,cascade = {CascadeType.MERGE,CascadeType.DETACH})
    @JoinColumn(name = "user_id", nullable = false)
    private User users;

    private Date expiryDate;

}
