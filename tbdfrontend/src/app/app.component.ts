import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  isClicked:boolean = false;
  title = 'tbdfrontend';

  constructor(private router: Router) { }
  ngOnInit() {
    this.isClicked=false;
  }
}
