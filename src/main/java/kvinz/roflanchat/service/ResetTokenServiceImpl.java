package kvinz.roflanchat.service;

import kvinz.roflanchat.controller.error_handler.custom_errors.InvalidTokenExeption;
import kvinz.roflanchat.domain.ResetToken;
import kvinz.roflanchat.domain.User;
import kvinz.roflanchat.domain.VerificationToken;
import kvinz.roflanchat.repository.ResetTokenRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@Data
public class ResetTokenServiceImpl implements ResetTokenService {

    @Autowired
    ResetTokenRepository resetTokenRepository;

    @Override
    public void saveResetToken(ResetToken token) {
        resetTokenRepository.save(token);
    }

    @Override
    public ResetToken findByToken(String token) {
        ResetToken resetToken = resetTokenRepository.findByToken(token);
        if (resetToken == null) {
            throw new InvalidTokenExeption("No such token");
        }

        Calendar cal = Calendar.getInstance();
        if (resetToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0) {
            throw new InvalidTokenExeption("Token expired");
        }
        return resetToken;
    }

    @Override
    public void removeToken(ResetToken token) {
        resetTokenRepository.delete(token);
    }

    @Override
    public void removeTokenByToken(String token) {
        resetTokenRepository.deleteByToken(token);
    }
}
