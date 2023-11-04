import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

    @Injectable({
        providedIn: 'root'
    })
    
    export class LoginService {
        private backendUrl = 'http://localhost:8080/user/signin'; // Remplacez par l'URL de votre backend
    
        constructor(private http: HttpClient) {}
    
        login(loginData: any): Observable<any> {
            console.log(loginData);
            console.log('connexion..')
            return this.http.post(this.backendUrl, loginData, {withCredentials:true});
        }


        
    }