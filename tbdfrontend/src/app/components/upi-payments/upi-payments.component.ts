import {Component, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DataService } from '../../data.service';
import { UpiPaymentRequest } from '../../dto/UpiPaymentRequest';
import { Router } from '@angular/router';


@Component({
  selector: 'app-upi-payments',
  templateUrl: './upi-payments.component.html',
  styleUrls: ['./upi-payments.component.scss']
})
export class UpiPaymentsComponent implements OnInit {
  selectedFile !: File;
  constructor(private dataService: DataService,
  private router: Router) { }
  ngOnInit() {
  }

  onFileSelected(event:any) {
    this.selectedFile = event.target.files[0];
  }

   uploadImage() {
     if (!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login']);
      return;
    }
     var sessionId = localStorage.getItem('sessionId') as string;
     const reader = new FileReader();
     reader.onload = (e: any) => {
     const base64Image = e.target.result.split(',')[1];
     const binaryImage = atob(base64Image);
     const imageData = new Uint8Array(binaryImage.length);
     const imageDataArray = Array.from(imageData);

      
     for (let i = 0; i < binaryImage.length; i++) {
        imageData[i] = binaryImage.charCodeAt(i);
     }

     const upiPaymentRequest: UpiPaymentRequest = {
      qrImage:base64Image,
      sessionId: sessionId

     };
     this.dataService.upiPayment(upiPaymentRequest).subscribe(
           response => {
	     this.dataService.updateSession(true, response.sessionId);
           },
           error => {
             console.log(error);
           }
         );
     };
     reader.readAsDataURL(this.selectedFile);
   }
}





