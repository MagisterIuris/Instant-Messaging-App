package org.openapitools.client.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openapitools.client.model.MessageDTO;

import java.util.Arrays;
import java.util.List;


public class MessageDTOTest {
  @Test
  public void testMessageDTO() {
    MessageDTO messageDTO = new MessageDTO();

    messageDTO.setTimestamp(123456789L);
    messageDTO.setFrom("JohnDoe");

    Assertions.assertEquals(123456789L, messageDTO.getTimestamp());
    Assertions.assertEquals("JohnDoe", messageDTO.getFrom());
  }

  @Test
  public void timestampTest() {
    MessageDTO messageDTO = new MessageDTO();
    messageDTO.setTimestamp(123456789L);
    Assertions.assertEquals(123456789L, messageDTO.getTimestamp());
  }

  @Test
  public void fromTest() {
    MessageDTO messageDTO = new MessageDTO();
    messageDTO.setFrom("JohnDoe");
    Assertions.assertEquals("JohnDoe", messageDTO.getFrom());
  }

  @Test
  public void toTest() {
    MessageDTO messageDTO = new MessageDTO();
    List<String> toList = Arrays.asList("Alice", "Bob", "Charlie");
    messageDTO.setTo(toList);
    Assertions.assertEquals(toList, messageDTO.getTo());
  }

  @Test
  public void typeTest() {
    MessageDTO messageDTO = new MessageDTO();
    messageDTO.setType("text");
    Assertions.assertEquals("text", messageDTO.getType());
  }

  @Test
  public void bodyTest() {
    MessageDTO messageDTO = new MessageDTO();
    messageDTO.setBody("Hello, World!");
    Assertions.assertEquals("Hello, World!", messageDTO.getBody());
  }
}
