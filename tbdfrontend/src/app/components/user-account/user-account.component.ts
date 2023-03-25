import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {DataService} from "../../data.service";
import {QrRequest} from "../../dto/QrRequest";

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.scss']
})
export class UserAccountComponent implements OnInit {
  constructor(private router: Router, private dataService: DataService) {
  }
  ngOnInit() {
  }
  goToTransactions() {
    this.router.navigate(['transaction'])
  }
  generateQr(){
    let user = localStorage.getItem("userName");
    console.log(user)
    let qrRequest = {} as QrRequest;
    if (user){
      let sessionId = this.dataService.getSessionValues(user)
      console.log("Called qr method",user)
      qrRequest.sessionId = sessionId
      this.dataService.generateQr(qrRequest).subscribe(data => {
          alert(data.message)
        })

    }

  }
}
