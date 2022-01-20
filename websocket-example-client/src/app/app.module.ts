import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import { SigninComponent } from './components/signin/signin.component';
import {AppRoutingModule} from "./app-routing.module";
import { ChatComponent } from './components/chat/chat.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {DatePipe} from "@angular/common";
import {AuthInterceptorService} from "./service/auth-interceptor.service";

@NgModule({
  declarations: [
    AppComponent,
    SigninComponent,
    ChatComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
