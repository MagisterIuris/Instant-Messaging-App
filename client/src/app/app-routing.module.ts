import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './AuthGuard';
import { AppComponent } from './app.component';
import { InscriptionComponent } from './inscription/inscription.component';
import { ListeConversationComponent } from './liste-conversation/liste-conversation.component';
import { PageParametresComponent } from './page-parametres/page-parametres.component';
import { SeConnecterComponent } from './se-connecter/se-connecter.component';


const routes: Routes = [
  {path: '', component: AppComponent},
  {path: '', redirectTo: '/se-connecter', pathMatch: 'full' },
  {path: 'page-parametres', component: PageParametresComponent },
  {path: 'inscription', component:InscriptionComponent},
  {path: 'liste-conversation', component:ListeConversationComponent, canActivate:[AuthGuard]},
  {path: 'se-connecter', component:SeConnecterComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes),
    BrowserModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
