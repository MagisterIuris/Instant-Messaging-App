package fr.mightycode.cpoo.server.service;

import fr.mightycode.cpoo.server.model.MyUser;
import fr.mightycode.cpoo.server.model.Conversation;
import fr.mightycode.cpoo.server.repository.ConversationRepository;
import fr.mightycode.cpoo.server.repository.MyUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  @Autowired
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsManager userDetailsManager;
  private final MyUserRepository UserRepository;
  private final HttpServletRequest httpServletRequest;
  private final ConversationRepository conversationRepository;
  private final MyUserRepository myUserRepository;

  public boolean signup(String login, String password, String email, String nom, String prenom, String dateDeNaissance, String photo) {
    MyUser user = new MyUser();
    user.setLogin(login);
    user.setPassword(passwordEncoder.encode(password));
    user.setEmail(email);
    user.setNom(nom);
    user.setPrenom(prenom);
    user.setDateDeNaissance(dateDeNaissance);
    user.setPhoto(photo);
    // Autres attributs
    user.setConversations(new ArrayList<>());
    UserRepository.save(user);
    return true;
  }


  public int signin(final String login, final String password) throws ServletException {
    MyUser u = UserRepository.findByLogin(login);

    if (u == null) {
      // L'utilisateur n'existe pas dans la base de données
      return 1;
    }

    if (passwordEncoder.matches(password, u.getPassword())) {
      final HttpSession session = httpServletRequest.getSession(false);
      if (session != null)
        return 2; // L'user déjà connecté

      userDetailsManager.createUser(new User(login, u.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER"))));
      httpServletRequest.login(login, password);
      httpServletRequest.getSession(true);
      return 0; // Connexion réussie
    }

    return 3; // Mauvais mot de passe
  }


  public void signout() throws ServletException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {
      // L'utilisateur est authentifié, nous pouvons le déconnecter
      User u = (User) authentication.getPrincipal();
      userDetailsManager.deleteUser(u.getUsername());
      httpServletRequest.logout();
    } else {
      // L'utilisateur n'est pas authentifié, vous pouvez gérer cette situation comme vous le souhaitez
      // Vous pouvez générer une exception ou retourner un message d'erreur, par exemple.
      throw new ServletException("L'utilisateur n'est pas authentifié");
    }
  }


  public boolean delete(String login) throws ServletException{
    if (!userDetailsManager.userExists(login))
        return false;

    MyUser user = UserRepository.findByLogin(login);

    for (Conversation conv : user.getConversations()) {
        conv.getUsers().remove(user);
        conversationRepository.save(conv); 
        user.getConversations().remove(conv);
    }
    myUserRepository.save(user); 
    userDetailsManager.deleteUser(login);
    UserRepository.removeByLogin(login);
    this.signout();
    return true;
  }


  public String getUserLogin() {
    final HttpSession session = httpServletRequest.getSession(true);
    if (session != null)
      return null;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDetails){
      MyUser u = UserRepository.findByLogin(((UserDetails) principal).getUsername());
      return u.getLogin();
    } else {
      return "Principal is not an instance of UserDetails";
    }
  }
  public String getLogin() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails){
      MyUser u = UserRepository.findByLogin(((UserDetails) principal).getUsername());
      return u.getLogin();
    } else {
      return "Principal is not an instance of UserDetails";
    }
  }


}
