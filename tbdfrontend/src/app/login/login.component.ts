import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from './login-request';
import { DataService } from '../data.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginRequest: LoginRequest = new LoginRequest()
  constructor(private router: Router, private dataService: DataService) { }
  ngOnInit(): void {

  }
  login() {
    this.dataService.sendLoginDetails(this.loginRequest).subscribe(data => {
      if (data.success) {
        alert("Welcome " + data.firstName + " " + data.lastName)
      } else {
        alert("Login UnSuccessfull")
      }
    }, error => (
      alert("Please enter valid username and password")
    )
    )
  }
  goToReg() {
    this.router.navigate(['registration'])
  }

}
