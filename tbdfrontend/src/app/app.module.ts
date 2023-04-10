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
import { ThirdPartyQrComponent } from './components/third-party-qr/third-party-qr.component';
import { PaymentQrComponent } from './components/payment-qr/payment-qr.component';
import { AboutComponent } from './components/about/about.component';
import { StatementComponent } from './components/statement/statement.component';
import { InteracRegisterComponent } from './components/interac-register/interac-register.component';
import {UpiPaymentsComponent} from "./components/upi-payments/upi-payments.component";


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
    ThirdPartyQrComponent,
    PaymentQrComponent,
    AboutComponent,
    StatementComponent,
    InteracRegisterComponent,
    UpiPaymentsComponent
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
