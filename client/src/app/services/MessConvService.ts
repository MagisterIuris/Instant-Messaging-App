import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageDTO } from '../MessageDTO';
import { UserProfile } from '../user-profile.model';

@Injectable({
    providedIn: 'root'
    })
    export class MessConvService {
    private baseUrl = 'http://localhost:8080/message/getAllMessagesOfConversation';

    constructor(private http: HttpClient) {}
    getAllMessagesConv(users: UserProfile[]) {
        console.log("get des messages..")
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        // Envoyez le message au backend
        return this.http.post<MessageDTO[]>(this.baseUrl, users,{headers,withCredentials:true});
    }
}
