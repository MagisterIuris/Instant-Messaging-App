import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
    })
    export class AuthService {
    private backendUrl = 'http://localhost:8080/user'; // Remplacez par l'URL de votre point d'API

    constructor(private http: HttpClient) {}

    // Vérifie si l'utilisateur est authentifié côté serveur
    isAuthenticated(): Observable<boolean> {
        return this.http.get<boolean>(`${this.backendUrl}/is-authenticated`,{withCredentials:true});
    }
    }