import { Component } from '@angular/core';
import {Router} from "@angular/router";
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent {
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
