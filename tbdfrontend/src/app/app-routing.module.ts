import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import {RegistrationComponent} from "./components/registration/registration.component";
import {UserAccountComponent} from "./components/user-account/user-account.component";
import {TransactionsComponent} from "./components/transactions/transactions.component";


const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'registration',component:RegistrationComponent},
  {path:'user-account',component:UserAccountComponent},
  {path:'transactions',component:TransactionsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
