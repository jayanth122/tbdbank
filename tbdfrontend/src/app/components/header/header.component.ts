import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {DataService} from "../../data.service";
import {QrRequest} from "../../dto/QrRequest";
import {LogOutRequest} from "../../dto/LogOutRequest";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  @Input()
  isHomePage!: boolean;
  @Input()
  afterLogin!:boolean
 constructor(private router:Router,private dataService : DataService) {
 }
 ngOnInit() {

 }
 logOut() {
   if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
     this.router.navigate(['login'])
   }
   else{
     let logOutRequest = {} as LogOutRequest;
     logOutRequest.sessionId = localStorage.getItem('sessionId') as string
     this.dataService.logOut(logOutRequest).subscribe(
       (response) => {
         alert(response)
         localStorage.clear();
         this.router.navigate(['login'])
       },
       error => {
         console.error(error)
       }
     )

   }
 }
}
