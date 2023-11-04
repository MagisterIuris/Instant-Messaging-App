import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/AuthService';
import { LoginService } from '../services/LoginService';

@Component({
  selector: 'app-se-connecter',
  templateUrl: './se-connecter.component.html',
  styleUrls: ['./se-connecter.component.css']
})
export class SeConnecterComponent {
  title : string = "Log in to Buzz";
  usernameOrEmail: string = ""; // Propriété pour Username or email
  password: string = ""; // Propriété pour Password

  
  
  /*Code correspondant à la partie imporante dans html */
  /*
  showUsernameLabel: boolean = true;
  showPasswordLabel: boolean = true;

  onInput() {
    if (this.usernameOrEmail.length > 0) {
      this.showUsernameLabel = false;
    } else {
      this.showUsernameLabel = true;
    }

    if (this.password.length > 0) {
      this.showPasswordLabel = false;
    } else {
      this.showPasswordLabel = true;
    }
  }
  */


  bees: { src: string; x: number; y: number; rotation: number }[] = [];
 

  ngOnInit() {
    this.generateRandomBees();
  }


  generateRandomBees() {
    const numberOfBees = 8; // Nombre d'abeilles
  for (let i = 0; i < numberOfBees; i++) {
    const randomX = Math.random() * window.innerWidth;
    const randomY = Math.random() * window.innerHeight;
    const randomRotation = Math.random() * 360; // Rotation aléatoire de 0 à 360 degrés

    if (i <= 3){
    this.bees.push({
      src: '../assets/bee1.png',
      x: randomX,
      y: randomY,
      rotation: randomRotation
    });
  }else{
    this.bees.push({
      src: '../assets/bee.png',
      x: randomX,
      y: randomY,
      rotation: randomRotation
    });
  }
  }
}

loginData = {
  login:'',
  password:''
}

constructor(private loginService: LoginService, private router:Router, private authService:AuthService){}

loginButton(){
  console.log(this.loginData);

    this.loginService.login(this.loginData).subscribe(response => {
      // Gérer la réponse du service d'authentification ici
      console.log("connexion...");
      this.authService.isAuthenticated().subscribe(isAuthenticated => {
        if (isAuthenticated) {
          // Rediriger vers la page souhaitée après la connexion
          this.router.navigate(['/liste-conversation']);
        } else {
          // Gérer les erreurs de connexion
          console.log("Erreur de connexion");
          // Vous pouvez également afficher un message d'erreur à l'utilisateur
        }
      });
    });
  
}



}


