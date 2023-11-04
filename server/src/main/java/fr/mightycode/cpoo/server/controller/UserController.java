package fr.mightycode.cpoo.server.controller;

import fr.mightycode.cpoo.server.dto.UserCredentialsDTO;
import fr.mightycode.cpoo.server.dto.UserProfileDTO;
import fr.mightycode.cpoo.server.model.Conversation;
import fr.mightycode.cpoo.server.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

  private final UserService userService;

  private final HttpServletRequest httpServletRequest;


  @PostMapping(value = "signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, String>> signup(@RequestBody final UserCredentialsDTO user) {
    try {
      boolean connected = userService.signup(user.login(), user.password(), user.email(), user.nom(), user.prenom(), user.dateDeNaissance(), user.photo());
      if (!connected) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Already signed in");
      } else {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Connection réussie!!!");
        return ResponseEntity.ok(response);
      }
    } catch (DataIntegrityViolationException ex) {
      // Cette exception est déclenchée lorsqu'une contrainte d'intégrité des données est violée,
      // comme une violation d'index unique. Vous pouvez renvoyer un message d'erreur approprié.
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists !");
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
    }
  }



  @PostMapping(value = "signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, String>> signin(@RequestBody final UserCredentialsDTO user) {
    try {
      int signInResult = userService.signin(user.login(), user.password());
      if (signInResult == 0) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Connection réussie!!!" + user.login());
        return ResponseEntity.ok(response);
      } else if (signInResult == 1) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist");
      } else if (signInResult == 2) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Already signed in");
      } else if (signInResult == 3) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
      } else {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during sign-in");
      }
    } catch (final ServletException ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
    }
  }




  @GetMapping(value = "username")
  public String getUserName() {
    String login = userService.getLogin();
    if (login != null) {
      return login;
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
  }


  @GetMapping(value = "profile", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserProfileDTO profile(Principal user) {
    return new UserProfileDTO(user.getName());
  }

  @PostMapping(value = "signout")
  public void signout() {
    try {
      userService.signout();
    }
    catch (final ServletException ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
    }
  }

  @DeleteMapping(value = "/{login}")
  public void delete(@PathVariable("login") String login) throws ServletException{
    if (!userService.delete(login))
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
  }

  @GetMapping(value = "is-authenticated")
  public ResponseEntity<Boolean> isAuthenticated() {
      String login = userService.getLogin();
      return ResponseEntity.ok(login != null);
  }
}
