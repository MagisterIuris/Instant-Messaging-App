package org.openapitools.client.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.UserCredentialsDTO;

public class UserCredentialsDTOTest {
  private final UserCredentialsDTO model = new UserCredentialsDTO();

  /**
   * Model tests for UserCredentialsDTO
   */
  @Test
  public void testUserCredentialsDTO() {
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

  /**
   * Test the property 'password'
   */
  @Test
  public void passwordTest() {
    model.password(null);
    Assertions.assertNull(model.getPassword());

    String password = "TestPassword";
    model.password(password);
    Assertions.assertEquals(password, model.getPassword());
  }

  /**
   * Test the property 'email'
   */
  @Test
  public void emailTest() {
    model.email(null);
    Assertions.assertNull(model.getEmail());

    String email = "test@example.com";
    model.email(email);
    Assertions.assertEquals(email, model.getEmail());
  }

  /**
   * Test the property 'nom'
   */
  @Test
  public void nomTest() {
    model.nom(null);
    Assertions.assertNull(model.getNom());

    String nom = "TestNom";
    model.nom(nom);
    Assertions.assertEquals(nom, model.getNom());
  }

  /**
   * Test the property 'prenom'
   */
  @Test
  public void prenomTest() {
    model.prenom(null);
    Assertions.assertNull(model.getPrenom());

    String prenom = "TestPrenom";
    model.prenom(prenom);
    Assertions.assertEquals(prenom, model.getPrenom());
  }

  /**
   * Test the property 'dateDeNaissance'
   */
  @Test
  public void dateDeNaissanceTest() {
    model.dateDeNaissance(null);
    Assertions.assertNull(model.getDateDeNaissance());

    String dateDeNaissance = "2000-01-01";
    model.dateDeNaissance(dateDeNaissance);
    Assertions.assertEquals(dateDeNaissance, model.getDateDeNaissance());
  }

  /**
   * Test the property 'photo'
   */
  @Test
  public void photoTest() {
    model.photo(null);
    Assertions.assertNull(model.getPhoto());

    String photo = "test.jpg";
    model.photo(photo);
    Assertions.assertEquals(photo, model.getPhoto());
  }
}
