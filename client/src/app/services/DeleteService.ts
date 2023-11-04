import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

    @Injectable({
        providedIn: 'root'
    })
    
    export class DeleteService {
        private backendUrl = 'http://localhost:8080/user'; // Remplacez par l'URL de votre backend
    
        constructor(private http: HttpClient) {}

        getCurrentUserLogin() {
            const options = {
                responseType: 'text' as 'json',
                withCredentials: true
            };
            
            return this.http.get<string>(`${this.backendUrl}/username`, options);
        }
    
        deleteMyUser(login:string) {
            const url = `${this.backendUrl}/${login}`;
            console.log(url);
            
            return this.http.delete(url,{withCredentials:true});
        }

        
    }