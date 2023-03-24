import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {DataService} from "../../data.service";

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.scss']
})
export class UserAccountComponent implements OnInit {
  constructor(private router: Router, private dataService: DataService) { }
  ngOnInit() {
  }
  goToTransactions() {
    this.router.navigate(['transaction'])
  }
  generateQr(){
    let user = localStorage.getItem("userName");
    if (user){
      let sessionId = this.dataService.getSessionValues(user)
      this.dataService.generateQr(sessionId).subscribe(data => {
        alert(data.message)
      })
    }

  }
}
