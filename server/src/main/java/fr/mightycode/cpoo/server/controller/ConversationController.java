package fr.mightycode.cpoo.server.controller;

import fr.mightycode.cpoo.server.dto.ConversationDisplayDTO;
import fr.mightycode.cpoo.server.dto.MessageDTO;
import fr.mightycode.cpoo.server.model.Conversation;
import fr.mightycode.cpoo.server.model.MyUser;
import fr.mightycode.cpoo.server.repository.MyUserRepository;
import fr.mightycode.cpoo.server.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("conversation")
@RequiredArgsConstructor
public class ConversationController {
  private final ConversationService conversationService;
  private final MyUserRepository myUserRepository;

  @PostMapping(value = "addConversation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ConversationDisplayDTO> addConversation(@RequestBody List<String> logins) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String principalLogin = authentication.getName();

    // Vérifiez que les utilisateurs existent
    List<MyUser> myUsers = new ArrayList<>();
    for (String login : logins) {
      MyUser user = myUserRepository.findByLogin(login);
      if (user == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found: " + login);
      }
      myUsers.add(user);
    }

    Conversation conversation = conversationService.addOrGetConversation(myUsers);
    ConversationDisplayDTO c;
    List<String> photos = new ArrayList();

    if (conversation != null) {
      // Ajouter les utilisateurs mutuellement dans leurs contacts
      for (MyUser user : myUsers) {
        for (MyUser otherUser : myUsers) {
          if (user != otherUser) {
            user.getContacts().add(otherUser);
          }
        }
        myUserRepository.save(user);
      }

      String lastMessageBody = null;
      if (!conversation.isEmpty()) {
        lastMessageBody = conversation.getLastMessage().getBody();
      }

      // Retirer l'utilisateur principal de la liste logins pour la construction de ConversationDisplayDTO
      logins.remove(principalLogin);

      // Ajouter les photos des utilisateurs (en excluant l'utilisateur principal)
      for (MyUser user : myUsers) {
        if (!user.getLogin().equals(principalLogin)) {
          photos.add(user.getPhoto());
        }
      }

      // Si vous voulez supprimer l'utilisateur principal de myUsers, vous pouvez le faire ici
      myUsers.removeIf(user -> user.getLogin().equals(principalLogin));

      c = new ConversationDisplayDTO(conversation.getId(), principalLogin,  logins, photos, lastMessageBody, System.currentTimeMillis());
      return ResponseEntity.ok(c);
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Conversation not created");
    }
  }



  @GetMapping(value = "getAllConversationsOfUser", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ConversationDisplayDTO>> getAllConversationsOfUser() {
    List<ConversationDisplayDTO> c = conversationService.getAllConversationDTOCurrentUser();
    if (!c.isEmpty()) {
      return ResponseEntity.ok(c);
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Current user does not have any conversation");
    }
  }

}