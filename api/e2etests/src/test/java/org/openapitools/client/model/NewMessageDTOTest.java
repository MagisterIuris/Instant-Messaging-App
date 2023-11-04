package org.openapitools.client.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.NewMessageDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewMessageDTOTest {
  private final NewMessageDTO model = new NewMessageDTO();

  /**
   * Model tests for NewMessageDTO
   */
  @Test
  public void testNewMessageDTO() {
    List<String> toList = Arrays.asList("Alice", "Bob", "Charlie");
    model.setTo(toList);
    model.setType("text");
    model.setBody("Hello, World!");

    Assertions.assertEquals(toList, model.getTo());
    Assertions.assertEquals("text", model.getType());
    Assertions.assertEquals("Hello, World!", model.getBody());
  }

  /**
   * Test the property 'to'
   */
  @Test
  public void toTest() {
    List<String> toList = Arrays.asList("Alice", "Bob", "Charlie");
    model.setTo(toList);

    Assertions.assertEquals(toList, model.getTo());
  }

  /**
   * Test the property 'type'
   */
  @Test
  public void typeTest() {
    model.setType("text");

    Assertions.assertEquals("text", model.getType());
  }

  /**
   * Test the property 'body'
   */
  @Test
  public void bodyTest() {
    model.setBody("Hello, World!");

    Assertions.assertEquals("Hello, World!", model.getBody());
  }
}
