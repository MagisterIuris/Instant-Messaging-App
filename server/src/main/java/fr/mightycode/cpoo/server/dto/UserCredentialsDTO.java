package fr.mightycode.cpoo.server.dto;

public record UserCredentialsDTO(String login, String password, String email, String nom, String prenom, String dateDeNaissance, String photo) {
}
