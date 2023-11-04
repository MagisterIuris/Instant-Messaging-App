package fr.mightycode.cpoo.server.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.UUID;

@Data
@Entity
@Table(name = "MyUser")
public class MyUser {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "user_id")
  private UUID id;

  @Column(name = "login", nullable = false, unique = true)
  private String login;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "nom", nullable = false)
  private String nom;

  @Column(name = "prenom", nullable = false)
  private String prenom;

  @Column(name = "date_de_naissance", nullable = false)
  private String dateDeNaissance;

  @Column(name = "photo", nullable = false)
  private String photo;

  @Column(name = "preference_mode")
  private String preferenceMode;

  @Column(name = "preference_langue")
  private String preferenceLangue;


  @Column(name = "status")
  private String status;

  @JsonManagedReference
  @ManyToMany(mappedBy = "users")
  private List<Conversation> conversations;


  @ManyToMany
  @JoinTable(
    name = "user_relations",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "related_user_id")
  )
  private List<MyUser> contacts;


  public MyUser() {
    // Constructeur par défaut pour JPA
  }

  public MyUser(String login, String password, String email, String nom, String prenom, String dateDeNaissance, String photo, String preferenceMode, String preferenceLangue, String status) {
    this.login = login;
    this.email = email;
    this.password = password;
    this.photo = photo;
    this.preferenceMode = preferenceMode;
    this.preferenceLangue = preferenceLangue;
    this.nom = nom;
    this.prenom = prenom;
    this.dateDeNaissance = dateDeNaissance;
    this.contacts = new ArrayList<>();
    this.status = status;
  }
}
//En spring, les getters et setters sont automatiques
