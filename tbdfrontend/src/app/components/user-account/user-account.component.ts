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
  firstName : string;
  lastName : string;
  constructor(private router: Router, private dataService: DataService) {
    this.firstName = ''
    this.lastName = ''
   // this.fetchCustomerDetails();
  }

  ngOnInit() {
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
    this.setFirstName(this.dataService.getFirstName())
    this.setLastName(this.dataService.getLastName())
  }
  setFirstName(name:string) {
    this.firstName = name;
  }

  setLastName(name:string) {
    this.lastName = name;
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

