package org.openapitools.client.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.UserProfileDTO;

public class UserProfileDTOTest {
  private final UserProfileDTO model = new UserProfileDTO();

  /**
   * Model tests for UserProfileDTO
   */
  @Test
  public void testUserProfileDTO() {
    Assertions.assertNotNull(model);
  }

  /**
   * Test the property 'login'
   */
  @Test
  public void loginTest() {
    model.login(null);
    Assertions.assertNull(model.getLogin());

    String login = "TestLogin";
    model.login(login);
    Assertions.assertEquals(login, model.getLogin());
  }
}
