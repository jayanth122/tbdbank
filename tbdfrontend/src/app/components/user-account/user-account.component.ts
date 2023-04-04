import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {DataService} from "../../data.service";
import {QrRequest} from "../../dto/QrRequest";
import {UserDetailsRequest} from "../../dto/UserDetailsRequest";

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.scss']
})
export class UserAccountComponent implements OnInit {
  public firstName : string;
  public lastName : string;
  constructor(private router: Router, private dataService: DataService) {
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
    this.firstName = dataService.firstName;
    this.lastName = dataService.lastName;
  }

  ngOnInit() {
    this.fetchCustomerDetails()
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

  generateQr() {
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
    const user = localStorage.getItem("userName");
    let qrRequest = {} as QrRequest;
    if (user) {
      qrRequest.sessionId = localStorage.getItem('sessionId') as string;
      this.dataService.generateQr(qrRequest).subscribe(data => {
        if (data.success) {
          alert(data.message)
          let newSessionId = data.sessionId
          //this.dataService.setSessionValues(user, newSessionId)
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

  fetchCustomerDetails()
  {
    const user = localStorage.getItem("userName");
    let userDetailsRequest = {} as UserDetailsRequest;
    if (user) {
      let sessionId = this.dataService.getSessionValues(user)
      userDetailsRequest.sessionId = sessionId
        this.dataService.fetchUserDetails(userDetailsRequest).subscribe(data => {
          if (data.success) {
            alert(data.message)
            let newSessionId = data.sessionId
            this.dataService.setSessionValues(user, newSessionId)
            this.dataService.user.accountBalance = data.customer.accountBalance
            this.dataService.user.email = data.customer.email
          }
          else{
            alert(data.message)
          }
        })
    }
  }
}

