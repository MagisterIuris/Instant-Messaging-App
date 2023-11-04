import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

    @Injectable({
        providedIn: 'root'
    })
    
    export class ConvService {
        private backendUrl = 'http://localhost:8080/conversation/addConversation'; // Remplacez par l'URL de votre backend
    
        constructor(private http: HttpClient) {}
    
        addConversation(users: string[]) {
            const url = this.backendUrl;
            return this.http.post(url, users,{withCredentials:true});
        }
    }