package kvinz.roflanchat.repository;

import kvinz.roflanchat.domain.GeneralChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralChatRepository extends JpaRepository<GeneralChat,Long> {
}
