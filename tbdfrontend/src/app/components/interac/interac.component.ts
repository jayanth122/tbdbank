import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from '../../data.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-interac',
  templateUrl: './interac.component.html',
  styleUrls: ['./interac.component.scss']
})
export class InteracComponent {
  toEmail: string = '';
  amount: number = 0;
  message: string = '';
  securityQuestion: string = '';
  securityAnswer: string = '';
  accountBalance: number = 0;
  interacSent: boolean = false;
  interacForm: FormGroup;
  balance:number=100;
  customerid:string='';


  constructor(
    private router: Router,
    private dataService: DataService,
    private formBuilder: FormBuilder
  ) {
    this.interacForm = this.formBuilder.group({
      toEmail: ['', Validators.required],
      amount: ['', Validators.required],
      message:[''],
      securityQuestion: ['', Validators.required],
      securityAnswer: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    // Retrieve customerId from local storage
    const customerId = localStorage.getItem('customerId');
    if (customerId !== null) {
      this.customerid = customerId;
    }
    console.log('customerId:', customerId);
    if (customerId != null) {
      // Call getAccountBalance method to retrieve account balance
      this.dataService.getAccountBalance(customerId).subscribe((data) => {
        console.log('getAccountBalance data:', data);
        this.accountBalance = data.accountBalance;
        // Display the current balance to the user
        this.displayCurrentBalance();
      });
    } else {
      console.log('customerId not found');
    }
  }

  displayCurrentBalance() {
    console.log('Account balance:', this.accountBalance);
    // TODO: Display the account balance to the user
  }

  sendInteracTransfer() {
    console.log('Your interac sent successfully');
    // TODO: Implement interac transfer logic here
    // Set flag to indicate that interac transfer has been sent
    this.interacSent = true;
    // Display success message to the user
    this.message = 'Interac transfer sent successfully!';
  }

  onSubmit() {
    if (this.interacForm.valid) {
      // Call sendInteracTransfer function to send the interac transfer
      this.sendInteracTransfer();
    }
  }
}
