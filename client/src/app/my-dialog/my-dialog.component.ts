import { Component, EventEmitter, Output } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Conversation2 } from '../Conversation2';
import { MessageDTO } from '../MessageDTO';
import { ConvService } from '../services/ConvService';
import { ConversationService } from '../services/ConversationService';
import { GetMessageService } from '../services/GetMessageService';
import { MessConvService } from '../services/MessConvService';
import { MessageService } from '../services/MessageService';
import { ThemeService } from '../theme.service';

@Component({
  selector: 'app-my-dialog',
  templateUrl: './my-dialog.component.html',
  styleUrls: ['./my-dialog.component.css']
})
export class MyDialogComponent {
  @Output() conversationAdded = new EventEmitter<void>()
  user1!:string;
  user2!:string;
  messages: MessageDTO[] = [];

  constructor( private convService: ConvService,public themeService: ThemeService, private messageService: MessageService, public dialog:MatDialog,
              private conversationService:ConversationService, private messConvService:MessConvService, public getMessageService:GetMessageService,
              public dialogRef:MatDialogRef<MyDialogComponent>){
    this.user1='';
    this.user2='';
  }

  

addConversationButton(){
  const userData = [this.user1, this.user2];
  this.convService.addConversation(userData).subscribe((response: any) => {
    // Gérez la réponse du backend ici, par exemple, mettez à jour votre interface utilisateur
    console.log('Réponse du backend :', response);
  
    // Créez une nouvelle instance de Conversation2 et copiez les propriétés pertinentes de la réponse
    const newConversation = new Conversation2();
    newConversation.id=response.id;
    newConversation.from=response.from;
    newConversation.usernames = response.usernames;
    newConversation.pictures = response.pictures;
    newConversation.lastMessage = response.lastMessage;
    newConversation.timestamp = response.timestamp;
    this.conversationService.addConversation(newConversation);
    this.conversationAdded.emit();
  });
  
}

confirmAdd(): void {
  this.dialogRef.close('delete');
}

}
