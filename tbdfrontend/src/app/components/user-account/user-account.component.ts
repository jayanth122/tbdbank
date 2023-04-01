import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {DataService} from "../../data.service";
import {QrRequest} from "../../dto/QrRequest";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { Buffer } from 'buffer/';
import { NavigationExtras } from '@angular/router';
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
    this.firstName = dataService.firstName;
    this.lastName = dataService.lastName;
  }

  ngOnInit() {
    this.fetchCustomerDetails();
  }

  goToTransactions() {
    this.router.navigate(['transaction'])
  }
  goToInterac()
  {
    this.router.navigate(['interac'])
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
          this.dataService.setPaymentQrImage(data.qrImage);
          this.dataService.setPaymentQrPdf(data.qrPdf)
          this.router.navigate(['qr'])
        } else {
          alert(data.message)
        }
      })
    }
  }
  // generateQr() {
  //   const user = localStorage.getItem("userName");
  //   let qrRequest = {} as QrRequest;
  //   if (user) {
  //     let sessionId = this.dataService.getSessionValues(user)
  //     qrRequest.sessionId = sessionId
  //     this.dataService.generateQr(qrRequest).subscribe(data => {
  //       if (data.success) {
  //         alert(data.message)
  //         let newSessionId = data.sessionId
  //         this.dataService.setSessionValues(user, newSessionId)
  //         this.dataService.setPaymentQrImage(data.qrImage);
  //         this.dataService.setPaymentQrPdf(data.qrPdf)
  //         this.router.navigate(['qr'])
  //       } else {
  //         alert(data.message)
  //       }
  //     })
  //   }
  // }
}

