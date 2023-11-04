// conversation.service.ts
import { Injectable } from '@angular/core';
import { Conversation2 } from '../Conversation2';
import { UserProfile } from '../user-profile.model';

@Injectable({
    providedIn: 'root'
    })
    export class ConversationService {
    public conversations: Conversation2[]=[];
    conversationParticipants: { [conversationId: string]: UserProfile[] } = {};

    getConversations() {
        return this.conversations;
    }

    addConversation(conversation: Conversation2) {
        const existingConversation = this.conversations.find(c => (c.from === conversation.from && c.usernames[0]=== conversation.usernames[0]));

        // Si la conversation n'existe pas, ajoutez-la
        if (!existingConversation) {
            this.conversations.push(conversation);
            this.conversationParticipants[conversation.id] = [{ login: conversation.from }, { login: conversation.usernames[0] }];
        } else {
          // Si la conversation existe déjà, vous pouvez choisir de gérer cette situation comme vous le souhaitez
          // Par exemple, afficher un message d'erreur ou ne rien faire.
            console.log('La conversation existe déjà dans la liste.');
        }
    }
    }
