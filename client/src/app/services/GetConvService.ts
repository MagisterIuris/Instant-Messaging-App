import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Conversation2 } from '../Conversation2';

@Injectable({
    providedIn: 'root',
    })
    export class GetConvService {
    private backendUrl = 'http://localhost:8080/conversation/getAllConversationsOfUser'; // Remplacez par l'URL de votre backend

    constructor(private http: HttpClient) {}

    getAllConversationsOfUser(): Observable<Conversation2[]> {
    console.log("y");
    return this.http.get<Conversation2[]>(this.backendUrl, {withCredentials: true});
    }
    }
