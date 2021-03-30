package kvinz.roflanchat.service.email;

import kvinz.roflanchat.domain.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendVerificationEmail(User user);
    void sendResetEmail(User user);
    void newPasswordEmail(User user,String password);
}
