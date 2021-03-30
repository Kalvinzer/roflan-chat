package kvinz.roflanchat.repository;

import kvinz.roflanchat.domain.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetTokenRepository extends JpaRepository<ResetToken,Long> {
    ResetToken findByToken(String token);
    void deleteByToken(String token);
}
