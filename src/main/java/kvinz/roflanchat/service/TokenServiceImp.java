package kvinz.roflanchat.service;

import kvinz.roflanchat.domain.VerificationToken;
import kvinz.roflanchat.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TokenServiceImp implements TokenService {

    @Autowired
    TokenRepository tokenRepository;


    @Override
    public void saveSecureToken(VerificationToken token) {
        tokenRepository.save(token);
    }

    @Override
    public VerificationToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void removeToken(VerificationToken token) {
        tokenRepository.delete(token);
    }

    @Override
    public void removeTokenByToken(String token) {
        tokenRepository.removeByToken(token);
    }

}
