import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
    })
    export class MessageService {
    private baseUrl = 'http://localhost:8080/message'; // Remplacez par l'URL de votre backend

    constructor(private http: HttpClient) {}
    envoyerMessage(messageData: any): Observable<any> {
        console.log("Envoi du message..")
        // Envoyez le message au backend
        return this.http.post<any>(`${this.baseUrl}`, messageData, {withCredentials:true});
    }
}
