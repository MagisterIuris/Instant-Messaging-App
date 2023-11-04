package fr.mightycode.cpoo.server.repository;

import org.springframework.transaction.annotation.Transactional;
import fr.mightycode.cpoo.server.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, UUID> {
  MyUser findByLogin(String login);

  @Transactional
  void removeByLogin(String login);
}
