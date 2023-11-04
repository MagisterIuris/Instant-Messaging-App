package fr.mightycode.cpoo.server.service;

import fr.mightycode.cpoo.server.dto.ConversationDisplayDTO;
import fr.mightycode.cpoo.server.dto.UserProfileDTO;
import fr.mightycode.cpoo.server.model.Conversation;
import fr.mightycode.cpoo.server.model.Message;
import fr.mightycode.cpoo.server.model.MyUser;
import fr.mightycode.cpoo.server.repository.ConversationRepository;
import fr.mightycode.cpoo.server.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationService {
  private final ConversationRepository conversationRepository;
  private final MyUserRepository myUserRepository;
  private final MessageService messageService;

  public Conversation addOrGetConversation(List<MyUser> users) {
    if (users.size() < 2) {
        return null; // Il faut au moins 2 utilisateurs pour une conversation
    }

    // Utilisez un ensemble pour stocker les utilisateurs (élimine les doublons)
    Set<MyUser> uniqueUsers = new HashSet<>(users);

    if (uniqueUsers.size() < 2) {
        return null; // Après suppression des doublons, il faut toujours au moins 2 utilisateurs
    }

    // Trier les utilisateurs pour garantir un ordre cohérent
    List<MyUser> sortedUsers = uniqueUsers.stream()
            .sorted(Comparator.comparing(MyUser::getLogin))
            .collect(Collectors.toList());

    List<UUID> userIds = sortedUsers.stream()
            .map(MyUser::getId)
            .collect(Collectors.toList());

    // Vérifiez s'il existe déjà une conversation entre ces utilisateurs
    List<Conversation> existingConversations = conversationRepository.findConversationByUsersIn(userIds);

    for (Conversation existingConversation : existingConversations) {
        // Vérifiez que cette conversation a exactement les mêmes utilisateurs
        if (existingConversation.getUsers().size() == sortedUsers.size() &&
            existingConversation.getUsers().containsAll(sortedUsers)) {
            // Si la conversation existante a des messages, renvoyez-la
            if (!existingConversation.getMessages().isEmpty()) {
                return existingConversation;
            }
        }
    }

    // Créez une nouvelle conversation
    Conversation conversation = new Conversation();
    conversation.setUsers(sortedUsers);
    conversationRepository.save(conversation);

    // Mettez à jour la liste de conversations de chaque utilisateur
    for (MyUser user : sortedUsers) {
        List<Conversation> userConversations = new ArrayList<>(user.getConversations());
        userConversations.add(conversation);
        user.setConversations(userConversations);
    }

    // Sauvegardez les modifications sur les utilisateurs
    myUserRepository.saveAll(sortedUsers);

    return conversation;
}




public List<ConversationDisplayDTO> getAllConversationDTOCurrentUser() {
  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  final String loginCurrentUser = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : null;

  List<Conversation> conversations = (loginCurrentUser != null)
    ? conversationRepository.findConversationsByUsersContaining(myUserRepository.findByLogin(loginCurrentUser))
    : new ArrayList<>();

  List<ConversationDisplayDTO> conversationDTOs = new ArrayList<>();

  for (Conversation conversation : conversations) {
    List<MyUser> users = conversation.getUsers();

    // Récupérez le dernier message de la conversation
    Message lastMessage = conversation.getLastMessage();

    if (lastMessage != null) {
      List<String> otherUserLogins = new ArrayList<>();
      List<String> otherUserPictures = new ArrayList();
      for (MyUser user : users) {
        if (!user.getLogin().equals(loginCurrentUser)) {
          otherUserLogins.add(user.getLogin());
          otherUserPictures.add(user.getPhoto()); // Ajoutez les photos des utilisateurs
        }
      }


      ConversationDisplayDTO conversationDTO = new ConversationDisplayDTO(UUID.randomUUID(),
          loginCurrentUser,
          otherUserLogins,
          otherUserPictures,
          lastMessage.getBody(),
          lastMessage.getTimestamp()
      );
      conversationDTOs.add(conversationDTO);
    
    }
  }

  return conversationDTOs;
}



  public void deleteConversation(Conversation c) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    final String usernameCurrentUser = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : null;

    if (usernameCurrentUser != null && c.getUsers().stream().anyMatch(user -> user.getLogin().equals(usernameCurrentUser))) {
      conversationRepository.delete(c);
    }
  }

  public void updateConversation(Conversation conversation) {
    conversationRepository.save(conversation);
  }

  public UUID getConversationIdByUsernames(List<UserProfileDTO> users) {
    // Assurez-vous que users contient au moins un utilisateur
    if (users.isEmpty()) {
      return null;
    }

    // Créez une liste de noms d'utilisateurs à partir de UserProfileDTO
    List<String> usernames = users.stream()
      .map(UserProfileDTO::login)
      .collect(Collectors.toList());

    // Recherchez la conversation en fonction des noms d'utilisateur
    List<Conversation> conversations = conversationRepository.findConversationsByUsernames(usernames, (long)users.size());

    if (conversations.isEmpty()) {
      return null; // Aucune conversation trouvée
    } else {
      // Retournez l'ID de la première conversation trouvée
      return conversations.get(0).getId();
    }
  }


}



