import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from '../../data.service';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { Buffer } from 'buffer/';
import { NavigationExtras } from '@angular/router';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.scss']
})
export class UserAccountComponent implements OnInit {
  customerId: string | null = localStorage.getItem('customerId');
  balance: number = 0;

  constructor(private dataService: DataService,  private router: Router,) {}

  ngOnInit(): void {
    if (this.customerId !== null) {
      this.dataService.getAccountBalance(this.customerId).subscribe(balance => {
        this.balance = balance.accountBalance;
        console.log(this.customerId);
      });
    }
    else{
      console.log("custmer id is null");
    }
  }
  goToInterac() {
    this.router.navigate(['interac'])
  }

}
