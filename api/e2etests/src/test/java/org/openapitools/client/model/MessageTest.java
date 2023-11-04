package org.openapitools.client.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openapitools.client.model.Message;

import java.util.Arrays;
import java.util.List;

public class MessageTest {
  private final Message model = new Message();

  @Test
  public void testMessage() {
    Message message = new Message();

    message.setId("message123");
    message.setTimestamp(123456789);
    message.setFrom("JohnDoe");
    List<String> toList = Arrays.asList("Alice", "Bob", "Charlie");
    message.setTo(toList);
    message.setType("text");
    message.setBody("Hello, World!");

    Assertions.assertEquals("message123", message.getId());
    Assertions.assertEquals(123456789, message.getTimestamp());
    Assertions.assertEquals("JohnDoe", message.getFrom());
    Assertions.assertEquals(toList, message.getTo());
    Assertions.assertEquals("text", message.getType());
    Assertions.assertEquals("Hello, World!", message.getBody());
  }


  @Test
  public void idTest() {
    Message message = new Message();
    message.setId("message123");
    Assertions.assertEquals("message123", message.getId());
  }

  @Test
  public void timestampTest() {
    Message message = new Message();
    message.setTimestamp(123456789);
    Assertions.assertEquals(123456789, message.getTimestamp());
  }

  @Test
  public void fromTest() {
    Message message = new Message();
    message.setFrom("JohnDoe");
    Assertions.assertEquals("JohnDoe", message.getFrom());
  }

  @Test
  public void toTest() {
    Message message = new Message();
    List<String> toList = Arrays.asList("Alice", "Bob", "Charlie");
    message.setTo(toList);
    Assertions.assertEquals(toList, message.getTo());
  }

  @Test
  public void typeTest() {
    Message message = new Message();
    message.setType("text");
    Assertions.assertEquals("text", message.getType());
  }

  @Test
  public void bodyTest() {
    Message message = new Message();
    message.setBody("Hello, World!");
    Assertions.assertEquals("Hello, World!", message.getBody());
  }
}
