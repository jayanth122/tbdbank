import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {DataService} from "../../data.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent {
  pattern = /^\/#?([a-zA-Z]*)$/;
  constructor(private router: Router, private dataService:DataService,private sanitizer:DomSanitizer){
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid && !this.pattern.test(this.router.url)) {
      this.router.navigate(['login'])
    }
  }
}
