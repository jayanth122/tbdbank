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
    const user = localStorage.getItem("userName");
    let qrRequest = {} as QrRequest;
    if (user){
      let sessionId = this.dataService.getSessionValues(user)
      qrRequest.sessionId = sessionId
      this.dataService.generateQr(qrRequest).subscribe(data => {
        if(data.success){
          alert(data.message)
          let newSessionId = data.sessionId
          this.dataService.setSessionValues(user,newSessionId)
        }
        else{
          alert(data.message)
        }
        })

    }

  }
}
