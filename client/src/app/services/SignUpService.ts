import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

    @Injectable({
        providedIn: 'root'
    })
    
    export class SignUpService {
        private backendUrl = 'http://localhost:8080/user/signup';
    
        constructor(private http: HttpClient) {}
    
        signinMyUser(signUpData: any): Observable<any> {
            console.log(signUpData);
            return this.http.post(this.backendUrl, signUpData, {withCredentials:true});
        }
    }