import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import {RegistrationComponent} from "./components/registration/registration.component";
import {UserAccountComponent} from "./components/user-account/user-account.component";
import {TransactionsComponent} from "./components/transactions/transactions.component";
import {InteracComponent} from "./components/interac/interac.component";
import {HomePageComponent} from "./components/home-page/home-page.component";
import {ThirdPartyQrComponent} from "./components/third-party-qr/third-party-qr.component";
import {PaymentQrComponent} from "./components/payment-qr/payment-qr.component";
import {AboutComponent} from "./components/about/about.component";


const routes: Routes = [
  {path:'', component : AboutComponent},
  {path:'login', component : LoginComponent},
  {path:'registration',component : RegistrationComponent},
  {path:'user-account',component : UserAccountComponent},
  {path:'interac', component:InteracComponent},
  {path:'transaction',component : TransactionsComponent},
  {path:'verification',component:ThirdPartyQrComponent},
  {path:'qr',component:PaymentQrComponent},
  {path:'about',component:AboutComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
