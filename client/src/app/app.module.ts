import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { AuthGuard } from './AuthGuard';
import { AppComponent } from './app.component';
import { DeleteConfirmationDialogComponent } from './delete-confirmation-dialog/delete-confirmation-dialog.component';
import { InscriptionComponent } from './inscription/inscription.component';
import { ListeConversationComponent } from './liste-conversation/liste-conversation.component';
import { MaterialModule } from './material/material.module';
import { MyDialogComponent } from './my-dialog/my-dialog.component';
import { NavigationComponent } from './navigation/navigation.component';
import { PageParametresComponent } from './page-parametres/page-parametres.component';
import { SeConnecterComponent } from './se-connecter/se-connecter.component';
import { AuthService } from './services/AuthService';
import { ConvService } from './services/ConvService';
import { DeleteService } from './services/DeleteService';
import { LoginService } from './services/LoginService';
import { LogoutService } from './services/LogoutService';
import { MessConvService } from './services/MessConvService';
import { MessageService } from './services/MessageService';
import { SignUpService } from './services/SignUpService';
import { ThemeService } from './theme.service';


@NgModule({
  declarations: [
    AppComponent,
    PageParametresComponent,
    InscriptionComponent,
    InscriptionComponent,
    NavigationComponent,
    ListeConversationComponent,
    SeConnecterComponent,
    DeleteConfirmationDialogComponent,
    MyDialogComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    RouterModule.forRoot( [{path: 'page-parametres', component: PageParametresComponent},
    {path: 'inscription', component:InscriptionComponent},
    {path: 'liste-conversation', component: ListeConversationComponent, canActivate: [AuthGuard]},
    {path:'se-connecter', component:SeConnecterComponent},
    {path: '', redirectTo: '/se-connecter', pathMatch: 'full' }]),
    MatSlideToggleModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule
  
  ],
  providers: [ThemeService,
              AuthService,
              DeleteService,
              LoginService,
              LogoutService,
              SignUpService,
              MessageService,
              ConvService,
              MessConvService],
  bootstrap: [AppComponent]
})
export class AppModule { }
