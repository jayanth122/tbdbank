import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {DataService} from "../../data.service";
import {QrRequest} from "../../dto/QrRequest";
import {UserDetailsRequest} from "../../dto/UserDetailsRequest";
import {InteracValidateRequest} from "../../dto/InteracValidateRequest";

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.scss']
})
export class UserAccountComponent implements OnInit {
  firstName : string;
  lastName : string;
  greeting: string = '';
  localTime: string = '';
  accountBalance:number;
  constructor(private router: Router, private dataService: DataService) {
    this.firstName = ''
    this.lastName = ''
    this.accountBalance = 0;
  }

  ngOnInit() {
    const currentDate = new Date();
    const hours = currentDate.getHours();
    if (hours < 12) {
      this.greeting = 'Good Morning';
    } else if (hours < 18) {
      this.greeting = 'Good Afternoon';
    } else {
      this.greeting = 'Good Evening';
    }
    this.localTime = currentDate.toLocaleString('en-US', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true });

    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
      this.setFirstName(this.dataService.getFirstName())
      this.setLastName(this.dataService.getLastName())
      this.setAccountBalance(this.dataService.getAccountBalance())
  }
  setFirstName(name:string) {
    this.firstName = name.replace(/"/g, '');
  }

  setLastName(name:string) {
    this.lastName = name.replace(/"/g, '');
  }
  setAccountBalance(balance:number){
    this.accountBalance = balance;
  }

  goToTransactions() {
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    } else {
      this.router.navigate(['transaction'])
    }
  }
  goToInterac()
  {
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    } else {
      this.router.navigate(['interac'])
    }

  }
  validateCustomerInterac() {
    let interacValidateRequest = {} as InteracValidateRequest;
    if (!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
    let sessionId = localStorage.getItem('sessionId') as string
    interacValidateRequest.sessionId = sessionId
    interacValidateRequest.email = ""
    this.dataService.validateInterac(interacValidateRequest).subscribe(data => {
      if (data.valid) {
        let newSessionId = data.sessionId
        this.dataService.updateSession(true, newSessionId)
        this.generateQr();
      } else {
        alert("Interac Not Registered")
        let newSessionId = data.sessionId
        this.dataService.updateSession(true, newSessionId)
        this.router.navigate(['interac-register'])
      }
    })
  }

  generateQr() {
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
    else{
      let qrRequest = {} as QrRequest;
        qrRequest.sessionId = localStorage.getItem('sessionId') as string;
        this.dataService.generateQr(qrRequest).subscribe(data => {
          if (data.success) {
            alert(data.message)
            let newSessionId = data.sessionId
            this.dataService.setPaymentQrImage(data.qrImage);
            this.dataService.setPaymentQrPdf(data.qrPdf)
            this.dataService.updateSession(true, newSessionId);
            this.router.navigate(['qr'])
          } else {
            alert(data.message)
          }
        })

    }
  }
}

