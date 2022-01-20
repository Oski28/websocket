import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {SigninComponent} from "./components/signin/signin.component";
import {ChatComponent} from "./components/chat/chat.component";
import {AuthGuard} from "./service/auth.guard";

const routes: Routes = [
  {path: '', redirectTo: '/signin', pathMatch: 'full'},
  {path: 'chat', component: ChatComponent, canActivate: [AuthGuard]},
  {path: 'signin', component: SigninComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {
}
