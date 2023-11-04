package fr.mightycode.cpoo.server.controller;

import fr.mightycode.cpoo.server.dto.MessageDTO;
import fr.mightycode.cpoo.server.dto.NewMessageDTO;
import fr.mightycode.cpoo.server.dto.UserProfileDTO;
import fr.mightycode.cpoo.server.model.Conversation;
import fr.mightycode.cpoo.server.model.Message;
import fr.mightycode.cpoo.server.model.MyUser;
import fr.mightycode.cpoo.server.repository.ConversationRepository;
import fr.mightycode.cpoo.server.repository.MessageRepository;
import fr.mightycode.cpoo.server.repository.MyUserRepository;
import fr.mightycode.cpoo.server.service.ConversationService;
import fr.mightycode.cpoo.server.service.MessageService;
import fr.mightycode.cpoo.server.service.RouterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {

  @Value("${cpoo.server.domain}")
  private String serverDomain;

  private final RouterService routerService;
  private final ConversationRepository conversationRepository;
  private final MessageRepository messageRepository;
  private final ConversationService conversationService;
  private final MessageService messageService;
  private final MyUserRepository myUserRepository;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public MessageDTO messagePost(final Principal user, @RequestBody final NewMessageDTO postMessage) {
      UUID uuid = UUID.randomUUID();
      long time = System.currentTimeMillis();
  
      // Build the user address with server domain
      String userAddress = user.getName() + "@" + serverDomain;
  
      // Get the current user's username
      String currentUsername = user.getName();
  
      // Get the recipient usernames
      List<String> recipientUsernames = postMessage.to();
      // Create a list of users with the current user and recipients
      List<MyUser> users = new ArrayList<>();
      users.add(myUserRepository.findByLogin(currentUsername));
  
      for (String recipientUsername : recipientUsernames) {
          MyUser recipient = myUserRepository.findByLogin(recipientUsername);
          if (recipient != null) {
              users.add(recipient);
          }
      }
  
      // Create a new conversation or retrieve an existing one
      Conversation conversation = conversationService.addOrGetConversation(users);
  
      Message message = new Message();
      message.setTimestamp(time);
      message.setFrom(userAddress);
      // Set the message "to" as a list of user addresses with server domain
      List<String> recipientAddresses = recipientUsernames.stream()
              .map(username -> username + "@" + serverDomain)
              .collect(Collectors.toList());
  
      // Instead of setting "to" as a list of strings directly, you should create a separate list
      List<String> recipientList = new ArrayList<>();
      recipientList.addAll(recipientAddresses);
  
      // Case management when list is empty (no recipients)
      if (recipientList.isEmpty()) {
          throw new IllegalArgumentException("Message must have at least one recipient.");
      }
  
      message.setTo(recipientList);
      message.setType(postMessage.type());
      message.setBody(postMessage.body());
      
      // Set the conversation for the message
      message.setConv(conversation);
  
      // Add the message to the conversation
      conversation.getMessages().add(message);
  
      // Save the message to the database
      messageService.storeMessage(message);
  
      // Update the conversation in the database (to ensure it includes the new message)
      conversationService.updateConversation(conversation);
  
      // Route the message to all recipients
      recipientAddresses.forEach(address -> {
          RouterService.Message routerMessage = new RouterService.Message(
                  uuid,
                  time,
                  userAddress,
                  Collections.singletonList(address), // Convertir la chaîne en liste
                  postMessage.type(),
                  postMessage.body()
          );
  
          routerService.routeMessage(routerMessage);
      });
  
      System.out.println(messageService.getMessages());
  
      // Return the message as a DTO
      return new MessageDTO(message);
  }
  




  @PostMapping(path = "getAllMessagesOfConversation", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Message>> getAllMessagesOfConversation(@RequestBody List<UserProfileDTO> users) {
    if (users.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least 2 users are required for a conversation.");
    }

    // Recherchez l'ID de conversation à partir des noms d'utilisateur ou des identifiants valides.
    UUID conversationId = conversationService.getConversationIdByUsernames(users);
    System.out.println(conversationId);

    if (conversationId == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No conversation found for the provided users.");
    }

    List<Message> messages = messageService.getAllMessagesOfConversation2(conversationId);
    System.out.println(messages);

    if (messages.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No messages found in the conversation.");
    } else {
      return ResponseEntity.ok(messages);
    }
  }







  @PostMapping(path = "getLastMessagesOfConversation", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Message> getLastMessagesOfConversation(@RequestBody List<UserProfileDTO> users) {
    // Vérifiez s'il y a au moins deux utilisateurs dans la liste
    if (users.size() < 2) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You need at least two users for a conversation");
    }

    // Extrayez les noms d'utilisateurs de la liste de UserProfileDTO
    List<String> usernames = users.stream()
      .map(UserProfileDTO::login)
      .collect(Collectors.toList());

    // Obtenez l'ID de la conversation entre ces utilisateurs
    UUID conversationId = conversationService.getConversationIdByUsernames(users);

    if (conversationId == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "There is no conversation between these users");
    }

    // Obtenez le dernier message de la conversation
    Message message = messageService.getLastMessageOfConversation(conversationId);

    if (message == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No message found in this conversation");
    } else {
      return ResponseEntity.ok(message);
    }
  }


}
