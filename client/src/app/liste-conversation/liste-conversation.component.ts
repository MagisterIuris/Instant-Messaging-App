import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { catchError, of } from 'rxjs';
import { Conversation2 } from '../Conversation2';
import { MessageDTO } from '../MessageDTO';
import { MyDialogComponent } from '../my-dialog/my-dialog.component';
import { ConversationService } from '../services/ConversationService';
import { DeleteService } from '../services/DeleteService';
import { GetConvService } from '../services/GetConvService';
import { GetMessageService } from '../services/GetMessageService';
import { MessConvService } from '../services/MessConvService';
import { MessageService } from '../services/MessageService';
import { ThemeService } from '../theme.service';
import { UserProfile } from '../user-profile.model';



@Component({
  selector: 'app-liste-conversation',
  templateUrl: './liste-conversation.component.html',
  styleUrls: ['./liste-conversation.component.css']
})
export class ListeConversationComponent implements OnInit {
  @ViewChild('conversationWindow') conversationWindow!: ElementRef;
  contenuConversation:String="";
  afficherChampMessage: boolean=false;
  messages: MessageDTO[] = [];
  filterConv : string = "";
  estEnLigne: Boolean = true;
  conversationActive2!: Conversation2;
  numberOfConversations!: number;
  userLogin:string='';
  userLogin2:string='';
  constructor(public themeService: ThemeService,
              private messageService: MessageService,
              public dialog:MatDialog,
              private conversationService:ConversationService,
              public getMessageService:GetMessageService,
              public messConvService:MessConvService,
              public deleteService:DeleteService,
              private getConvService: GetConvService) {}

  conversations2: Conversation2[]= this.conversationService.conversations;

  

  getAllMessConvButton() {
    const conversationParticipants = this.conversationService.conversationParticipants[this.conversationActive2.id];
    const user1 = conversationParticipants[0].login;
    const user2 = conversationParticipants[1].login;
    console.log(user1,user2);

      const users: UserProfile[] = [
        { login: user1 },
        { login: user2 }
      ];
      console.log(user1,user2);
      this.messConvService.getAllMessagesConv(users)
    .pipe(
      catchError(error => {
        console.error('Une erreur s\'est produite :', error);
        this.messages = [];
        return of([]);
      })
    )
    .subscribe((response: MessageDTO[]) => {
      this.messages = response; // Ajoutez chaque message à votre tableau de messages
      console.log(this.messages);
    });
    this.scrollToBottom();
    }

  messageData= {
    to: [] as string[],
    type: 'texte',
    body: ''
  };

  sendButton() {
    console.log("Envoi du message...");
    console.log(this.messageData);
    const tempmess={...this.messageData};
    this.messageData.body='';
    this.messageService.envoyerMessage(tempmess)
      .subscribe({
        next: (response:MessageDTO) => {
          console.log(response);
          this.messages.push(response)
          this.scrollToBottom();
        },
        error: (error) => {
          console.error('Erreur lors de l\'envoi du message :', error);
        },
      });
      
  }

  openDialog() {
    const dialogRef = this.dialog.open(MyDialogComponent, {
      width: '400px', // Ajustez la largeur selon vos besoins
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }
  
  ngOnInit() {
    // Charge le thème lors de l'initialisation du composant
    this.themeService.loadTheme();
    this.numberOfConversations = this.conversations2.length;
    this.deleteService.getCurrentUserLogin().subscribe((login: string) => {
      this.userLogin2=login;
      this.userLogin = `${login}@buzzchat`;
      console.log('User Login:', this.userLogin);
    });

    this.getConvService.getAllConversationsOfUser().subscribe({
      next: (conversations: Conversation2[]) => {
        conversations.forEach((conversation) => {
          console.log("liste conversation2");
          console.log(this.conversations2);
          console.log("liste conversations");
          console.log(conversations);
          this.conversationService.addConversation(conversation);
          //this.conversations2.push(conversation);
          
        });
        console.log("la liste des conversations");
        console.log(this.conversations2);
      },
      error: (error) => {
        console.error('Erreur lors de la récupération des conversations :', error);
      }
    });
  
  }
  

  onFilterChanged() {
    console.log('Valeur de filterConv :', this.filterConv);
    // Ajoutez ici votre code de filtrage des conversations
  }

  onItemClick2(event: Event, item: Conversation2) {
    event.stopPropagation(); // Empêche la propagation de l'événement de clic à d'autres éléments parent
    console.log('Élément cliqué : ' + item.usernames);
    this.contenuConversation = 'Vous avez cliqué sur : ' + item.usernames;
    this.afficherChampMessage = true;
    this.conversationActive2 = item;
    this.messageData.to[0]=item.usernames[0];
  }
  
  
  changerEtatConnexion() {
    this.estEnLigne = !this.estEnLigne;
  }

  scrollToBottom() {
    if (this.conversationWindow) {
      this.conversationWindow.nativeElement.scrollTop = this.conversationWindow.nativeElement.scrollHeight;
    }
  }
  }


