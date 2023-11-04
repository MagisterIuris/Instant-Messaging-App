package fr.mightycode.cpoo.server.model;

import fr.mightycode.cpoo.server.service.RouterService;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Data
@Entity
@Table(name = "messages")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;        // unique id of the message

  @Column(name = "timestamp", nullable = false)
  private long timestamp; // timestamp of the message

  @Column(name = "`from`", nullable = false)
  private String from;    // sender address

  @ElementCollection
  @CollectionTable(name = "message_recipients", joinColumns = @JoinColumn(name = "message_id"))
  @Column(name = "recipient_address")
  private List<String> to; // recipient addresses

  @ManyToOne
  @JoinColumn(name = "conversation_id")
  @ToString.Exclude
  @JsonIgnore
  private Conversation conv;

  @Column(name = "type", nullable = false)
  private String type;    // MIME type of the body

  @Column(name = "body", nullable = false)
  private String body;    // body (BASE64 encoded for binary types)

  public Message() {
    this.to = new ArrayList<>();
  }

  public Message(RouterService.Message routerMessage) {
    this.id = routerMessage.id();
    this.timestamp = routerMessage.timestamp();
    this.from = routerMessage.from();

    List<String> recipients = routerMessage.to();

    if (recipients.isEmpty() ) {
      throw new IllegalArgumentException("Message must have at least one recipient.");
    }

    this.to = recipients;
    this.type = routerMessage.type();
    this.body = routerMessage.body();
  }



}
