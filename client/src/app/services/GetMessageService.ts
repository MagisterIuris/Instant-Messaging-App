import { Injectable } from '@angular/core';

    @Injectable({
    providedIn: 'root',
    })
    export class GetMessageService {
    private _user1: string = '';
    private _user2: string = '';

    setUser1(user: string) {
        this._user1 = user;
    }

    setUser2(user: string) {
        this._user2 = user;
    }

    getUser1(): string {
        return this._user1;
    }

    getUser2(): string {
        return this._user2;
    }
    }