import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import {RegistrationComponent} from "./components/registration/registration.component";
import {UserAccountComponent} from "./components/user-account/user-account.component";
import { TransactionsComponent } from './components/transactions/transactions.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { InteracComponent } from './components/interac/interac.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    UserAccountComponent,
    TransactionsComponent,
    HeaderComponent,
    FooterComponent,
    InteracComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
