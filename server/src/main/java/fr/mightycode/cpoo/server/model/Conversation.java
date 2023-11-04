package fr.mightycode.cpoo.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator; // Import nécessaire pour le tri des messages

@Data
@Entity
@Table(name = "conversation")
public class Conversation {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "conversation_id")
  private UUID id; // Identifiant unique de la conversation

  @JsonBackReference
  @ManyToMany
  @JoinTable(
    name = "user_conversation",
    joinColumns = @JoinColumn(name = "conversation_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<MyUser> users; // Liste des utilisateurs participant à la conversation

  @OneToMany(mappedBy="conv")
  @ToString.Exclude
  private List<Message> messages;

  public Conversation() {
    this.users = new ArrayList<>();
    this.messages = new ArrayList<>();
  }

  public boolean equals(Conversation c) {
    if (c.getUsers().containsAll(this.users) && this.users.containsAll(c.getUsers())) {
      return true;
    }
    return false;
  }

  public boolean isEmpty() {
    return this.messages.isEmpty();
  }

  public Message getLastMessage() {
    if (messages.isEmpty()) {
      return null;
    }

    // Triez les messages par timestamp dans l'ordre décroissant
    messages.sort(Comparator.comparing(Message::getTimestamp).reversed());

    // Retournez le premier message (le dernier en fonction du timestamp)
    return messages.get(0);
  }
}
