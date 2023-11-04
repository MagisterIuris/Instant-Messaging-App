package fr.mightycode.cpoo.router.model;

import lombok.Data;

import java.util.UUID;

import java.util.List;

@Data
public class Message {
  private UUID id;            // unique id of the message
  private String from;        // sender address
  private List<String> to;    // recipient user addresses (list of email addresses)
  private String type;        // MIME type of the body
  private String body;        // body (BASE64 encoded for binary types)

}

