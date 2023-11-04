package fr.mightycode.cpoo.server.service;

import fr.mightycode.cpoo.server.model.Message;
import fr.mightycode.cpoo.server.model.MyUser;
import fr.mightycode.cpoo.server.repository.ConversationRepository;
import fr.mightycode.cpoo.server.repository.MessageRepository;
import fr.mightycode.cpoo.server.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import fr.mightycode.cpoo.server.model.Conversation;


import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

  @Value("${cpoo.server.domain}")
  private String serverDomain;

  private final MessageRepository messageRepository;
  private final ConversationRepository conversationRepository;

  /**
   * Store a message in the DB.
   *
   * @param message The message to store
   */
  public void storeMessage(Message message) {
    messageRepository.save(message);
  }

  /**
   * Get all messages send to or by the current user.
   *
   * @return the list of messages sent to or by the user
   */
  public List<Message> getMessages() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    final String usernameCurrentUser = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : null;

    if (usernameCurrentUser != null) {
      String userAddress = usernameCurrentUser + "@" + serverDomain;
      List<Message> messages = messageRepository.findByFromIgnoreCaseOrToInIgnoreCaseOrderByTimestampDesc(userAddress, Collections.singletonList(userAddress));
      return messages;
    } else {
      return null;
    }
  }


  public List<Message> getAllMessagesOfConversation2(UUID conversationId) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    final String usernameCurrentUser = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : null;

    if (usernameCurrentUser != null) {
      List<Message> messages = conversationRepository.findById(conversationId)
        .map(Conversation::getMessages)
        .orElse(null);

      return messages;
    } else {
      return null;
    }
  }

  public Message getLastMessageOfConversation(UUID conversationId) {
    List<Message> messages = conversationRepository.findById(conversationId)
      .map(Conversation::getMessages)
      .orElse(null);

    if (messages != null && !messages.isEmpty()) {
      return messages.get(messages.size() - 1);
    } else {
      return null;
    }
  }
}
