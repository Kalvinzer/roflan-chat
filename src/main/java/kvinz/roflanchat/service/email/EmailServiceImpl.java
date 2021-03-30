package kvinz.roflanchat.service.email;

import kvinz.roflanchat.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;


    private void formEmail(String toAddress,String fromAddress,String subject,String content){
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(fromAddress);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content, true);
            emailSender.send(message);
        }catch (Exception e){  }
    }

    @Override
    public void sendVerificationEmail(User user){
        String toAddress = user.getEmail();
        String fromAddress = "roflansender@gmail.com";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_blank\">VERIFY</a></h3><br>"
                + "or use this link:  [[URL]] <br>"
                + "Thank you.<br>";
        String verifyURL = "https://roflan-chat.herokuapp.com" + "/verify?code=" + user.getToken().getToken();

        content = content.replace("[[name]]", user.getUsername());

        content = content.replace("[[URL]]", verifyURL);
        formEmail(toAddress,fromAddress,subject,content);
    }

    @Override
    public void sendResetEmail(User user){
        String toAddress = user.getEmail();
        String fromAddress = "roflansender@gmail.com";
        String subject = "Password resetting";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset your password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_blank\">RESET</a></h3><br>"
                + "or use this link: [[URL]] <br>"
                + "Thank you.<br>";
        String verifyURL = "https://roflan-chat.herokuapp.com" + "/reset?code=" + user.getResetToken().getToken();

        content = content.replace("[[name]]", user.getUsername());

        content = content.replace("[[URL]]", verifyURL);
        formEmail(toAddress,fromAddress,subject,content);
    }

    @Override
    public void newPasswordEmail(User user,String password) {
        String toAddress = user.getEmail();
        String fromAddress = "roflansender@gmail.com";
        String subject = "New password";
        String content = "Dear [[name]],<br>"
                + "Your new password is: [[password]]<br>"
                + "Thank you.<br>";

        content = content.replace("[[name]]", user.getUsername());

        content = content.replace("[[password]]", password);

        formEmail(toAddress,fromAddress,subject,content);
    }
}
