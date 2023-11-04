import { Component } from '@angular/core';
import { SignUpService } from '../services/SignUpService';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.css']
})
export class InscriptionComponent {

  constructor(private signUpService: SignUpService) {}


    // Créez un objet pour stocker les données du formulaire
    signUpData = {
      login: '',
      password: '',
      email: '',
      nom: '',
      prenom: '',
      dateDeNaissance: '',
      photo:''
    };

    // Appelez la méthode du service pour s'inscrire
    signUpButton(){
      console.log("je tente de m'inscrire")
      console.log(this.signUpData);
      this.signUpService.signinMyUser(this.signUpData)
      .subscribe({
        next: response => {
          // Gérer la réponse ici
          console.log("Inscription...")
        },
        error: error => {
          // Gérer les erreurs ici
          console.error('Erreur lors de l\'inscription : ', error);
        }
      });
  

}
}