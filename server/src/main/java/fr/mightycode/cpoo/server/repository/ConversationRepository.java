package fr.mightycode.cpoo.server.repository;

import fr.mightycode.cpoo.server.model.Conversation;
import fr.mightycode.cpoo.server.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
  List<Conversation> findConversationsByUsersContaining(MyUser user);
  List<Conversation> findConversationByUsersIn(List<UUID> users);

  @Query("SELECT c FROM Conversation c JOIN c.users u WHERE u.login IN :usernames GROUP BY c HAVING COUNT(u) = :userCount")
  List<Conversation> findConversationsByUsernames(@Param("usernames") List<String> usernames, @Param("userCount") Long userCount);



}
