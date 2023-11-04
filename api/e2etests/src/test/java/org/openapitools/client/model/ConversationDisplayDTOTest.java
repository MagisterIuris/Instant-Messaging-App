package org.openapitools.client.model;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConversationDisplayDTOTest {


  @Test
  public void testConversationDisplayDTO() {
    ConversationDisplayDTO conversation = new ConversationDisplayDTO();
    assertNotNull(conversation);
  }

  @Test
  public void idTest() {
    ConversationDisplayDTO conversation = new ConversationDisplayDTO();
    conversation.setId("12345");
    assertNotNull(conversation.getId());
    assertEquals("12345", conversation.getId());
  }

  @Test
  public void fromTest() {
    ConversationDisplayDTO conversation = new ConversationDisplayDTO();
    conversation.setFrom("user1");
    assertNotNull(conversation.getFrom());
    assertEquals("user1", conversation.getFrom());
  }

  @Test
  public void usernamesTest() {
    List<String> usernames = new ArrayList<>();
    usernames.add("user1");
    usernames.add("user2");

    ConversationDisplayDTO conversation = new ConversationDisplayDTO();
    conversation.setUsernames(usernames);

    assertNotNull(conversation.getUsernames());
    assertEquals(usernames, conversation.getUsernames());
  }

  @Test
  public void picturesTest() {
    List<String> pictures = new ArrayList<>();
    pictures.add("pic1.jpg");
    pictures.add("pic2.jpg");

    ConversationDisplayDTO conversation = new ConversationDisplayDTO();
    conversation.setPictures(pictures);

    assertNotNull(conversation.getPictures());
    assertEquals(pictures, conversation.getPictures());
  }

  @Test
  public void lastMessageTest() {
    ConversationDisplayDTO conversation = new ConversationDisplayDTO();
    conversation.setLastMessage("Hello, how are you?");
    assertNotNull(conversation.getLastMessage());
    assertEquals("Hello, how are you?", conversation.getLastMessage());
  }

  @Test
  public void timestampTest() {
    ConversationDisplayDTO conversation = new ConversationDisplayDTO();
    conversation.setTimestamp(1635824100L);
    assertNotNull(conversation.getTimestamp());
    assertEquals(1635824100L, conversation.getTimestamp());
  }


}
