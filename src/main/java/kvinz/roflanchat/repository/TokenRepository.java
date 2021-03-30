package kvinz.roflanchat.repository;

import kvinz.roflanchat.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
    void removeByToken(String token);
}
