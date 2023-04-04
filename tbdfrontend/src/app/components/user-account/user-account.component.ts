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
    if(!this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
    this.firstName = dataService.firstName;
    this.lastName = dataService.lastName;
    console.log(this.firstName);
    console.log(this.lastName);
  }

  ngOnInit() {
    this.fetchCustomerDetails()
  }

  goToTransactions() {
    console.log("In GotoTransaction : loginValid = ", this.dataService.isLoginValid);
    if(this.dataService.isLoginValid) {
      this.router.navigate(['transaction'])
    } else {
      this.router.navigate(['login'])
    }
  }
  goToInterac()
  {
    if(this.dataService.isLoginValid) {
      this.router.navigate(['interac'])
    } else {
      this.router.navigate(['login'])
    }

  }

  generateQr() {
    if(!this.dataService.isLoginValid) {
      this.router.navigate(['interac'])
    }
    const user = localStorage.getItem("userName");
    let qrRequest = {} as QrRequest;
    if (user) {
      let sessionId = this.dataService.getSessionValues(user)
      qrRequest.sessionId = sessionId
      this.dataService.generateQr(qrRequest).subscribe(data => {
        if (data.success) {
          alert(data.message)
          let newSessionId = data.sessionId
          this.dataService.setSessionValues(user, newSessionId)
          this.dataService.setPaymentQrImage(data.qrImage);
          this.dataService.setPaymentQrPdf(data.qrPdf)
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

