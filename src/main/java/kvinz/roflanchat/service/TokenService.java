package kvinz.roflanchat.service;

import kvinz.roflanchat.domain.VerificationToken;

public interface TokenService {
    void saveSecureToken(final VerificationToken token);
    VerificationToken findByToken(final String token);
    void removeToken(final VerificationToken token);
    void removeTokenByToken(final String token);
}
