package kvinz.roflanchat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kvinz.roflanchat.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@ToString(exclude = "users")
public class VerificationTokenDTO {

    private static final int EXPIRATION = 24;

    private Long id;

    private String token;

    @JsonIgnore
    private User users;

    private Date expiryDate;

    public VerificationTokenDTO() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, EXPIRATION);
        expiryDate = calendar.getTime();
        token = UUID.randomUUID().toString();
    }
}
