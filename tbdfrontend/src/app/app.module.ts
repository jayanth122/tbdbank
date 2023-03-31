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
import { HomePageComponent } from './components/home-page/home-page.component';
<<<<<<< Updated upstream
import { ThirdPartyQrComponent } from './components/third-party-qr/third-party-qr.component';
import { PaymentQrComponent } from './components/payment-qr/payment-qr.component';


=======
import { AboutComponent } from './components/about/about.component';
>>>>>>> Stashed changes

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    UserAccountComponent,
    TransactionsComponent,
    HeaderComponent,
    FooterComponent,
    InteracComponent,
    HomePageComponent,
<<<<<<< Updated upstream
    ThirdPartyQrComponent,
    PaymentQrComponent
=======
    AboutComponent
>>>>>>> Stashed changes
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
