import {Component, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-upi-payments',
  templateUrl: './upi-payments.component.html',
  styleUrls: ['./upi-payments.component.scss']
})
export class UpiPaymentsComponent implements OnInit {
  selectedFile !: File;
  constructor() { }
  ngOnInit() {
  }

  onFileSelected(event:any) {
    this.selectedFile = event.target.files[0];
  }

  // uploadImage() {
  //   const reader = new FileReader();
  //   reader.onload = (e: any) => {
  //     const imageData = e.target.result.split(',')[1];
  //     const requestBody = {
  //       image: imageData
  //     };
  //     this.http.post('your-backend-api-url', requestBody)
  //       .subscribe(
  //         response => {
  //           console.log(response);
  //         },
  //         error => {
  //           console.log(error);
  //         }
  //       );
  //   };
  //   reader.readAsDataURL(this.selectedFile);
  // }
}





