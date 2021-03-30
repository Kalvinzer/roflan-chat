package kvinz.roflanchat.controller;

import kvinz.roflanchat.domain.ResetToken;
import kvinz.roflanchat.domain.User;
import kvinz.roflanchat.mapper.ResetTokenMapper;
import kvinz.roflanchat.mapper.UserMapper;
import kvinz.roflanchat.model.ResetTokenDTO;
import kvinz.roflanchat.model.UserDTO;
import kvinz.roflanchat.service.PasswordGenerator;
import kvinz.roflanchat.service.ResetTokenService;
import kvinz.roflanchat.service.UserService;
import kvinz.roflanchat.service.email.EmailService;
import lombok.AllArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.NotActiveException;

@RestController
@CrossOrigin
public class PasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResetTokenService resetTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    PasswordGenerator passwordGenerator;

    @PostMapping("/passwordReset")
    @Transactional
    public String resetPasswordAttempt(@RequestParam("email") String email) throws NotActiveException {
        UserDTO userDTO = userService.findUserByEmail(email);
        if(!userDTO.isEnabled()){
            throw new NotActiveException("User is not activated");
        }
        if(userDTO.getResetToken()==null){
            ResetTokenDTO resetToken = new ResetTokenDTO();
            System.out.println(resetToken);
            resetToken.setUsers(UserMapper.INSTANCE.userInfoDTOToUserInfo(userDTO));
            userDTO.setResetToken(resetToken);
            resetTokenService.saveResetToken(ResetTokenMapper.INSTANCE.toResetToken(resetToken));
        }
        emailService.sendResetEmail(UserMapper.INSTANCE.userInfoDTOToUserInfo(userDTO));
        return "ok";
    }

    @PutMapping("/passwordReset")
    @Transactional
    public String resetPassword(@RequestParam("token") String token){
        ResetToken resetToken = resetTokenService.findByToken(token);
        User user = resetToken.getUsers();
        String password = passwordGenerator.generatePassword();
        user.setPassword(password);
        user.setResetToken(null);
        userService.saveUser(UserMapper.INSTANCE.userInfoToUserInfoDTO(user));
        emailService.newPasswordEmail(user,password);
        return "ok";
    }

}
