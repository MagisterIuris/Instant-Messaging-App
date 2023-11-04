package org.openapitools.client.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.ResponseEntity;

public class ResponseEntityTest {
  private final ResponseEntity model = new ResponseEntity();

  /**
   * Model tests for ResponseEntity
   */
  @Test
  public void testResponseEntity() {
    Assertions.assertNotNull(model);
  }

  /**
   * Test the property 'headers'
   */
  @Test
  public void headersTest() {
    model.headers(null);
    Assertions.assertNull(model.getHeaders());

    Object headers = new Object();
    model.headers(headers);
    Assertions.assertEquals(headers, model.getHeaders());
  }

  /**
   * Test the property 'message'
   */
  @Test
  public void messageTest() {
    model.message(null);
    Assertions.assertNull(model.getMessage());

    String message = "Test Message";
    model.message(message);
    Assertions.assertEquals(message, model.getMessage());
  }
}
