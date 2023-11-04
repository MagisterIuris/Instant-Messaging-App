import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

    @Injectable({
        providedIn: 'root'
    })
    
    export class LogoutService {
        private backendUrl = 'http://localhost:8080/user/signout'; // Remplacez par l'URL de votre backend
    
        constructor(private http: HttpClient) {}
    
        signoutMyUer(data:any): Observable<any> {
            console.log("Deconnexion..");
            return this.http.post(this.backendUrl,data, {withCredentials:true});
        }
    }