import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'TBDBANK';
  isClicked:boolean = false;
  constructor(private router: Router) { }
  ngOnInit() {
    this.isClicked=false;
  }

  goToRegistration() {
    this.router.navigate(['registration'])
    this.isClicked=true;
  }
  goToLogin(){
    this.router.navigate(['login'])
    this.isClicked=true;
  }

}
