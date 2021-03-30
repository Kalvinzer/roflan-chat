package kvinz.roflanchat.service;

import kvinz.roflanchat.domain.ResetToken;

public interface ResetTokenService {
    void saveResetToken(final ResetToken token);
    ResetToken findByToken(final String token);
    void removeToken(final ResetToken token);
    void removeTokenByToken(final String token);
}
