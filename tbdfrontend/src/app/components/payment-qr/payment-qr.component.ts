import {Component, OnInit} from '@angular/core';
import {DataService} from "../../data.service";
import {DomSanitizer} from "@angular/platform-browser";
import { saveAs } from 'file-saver'
import {Router} from "@angular/router";

@Component({
  selector: 'app-payment-qr',
  templateUrl: './payment-qr.component.html',
  styleUrls: ['./payment-qr.component.scss']
})
export class PaymentQrComponent implements OnInit {
  public imageUrl : any;
  constructor(private router: Router, private dataService:DataService,private sanitizer:DomSanitizer){
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
  }
ngOnInit() {
  let imgBytes = this.dataService.getPaymentQrImage();
  let byteCharacters = atob(imgBytes);
 // let byteCharacters = new TextDecoder().decode(imgBytes);
  let byteNumbers = new Array(byteCharacters.length);
  for (let i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i);
  }
  let byteArray = new Uint8Array(byteNumbers);
  const blob = new Blob([byteArray], { type: 'image/png' });
  URL.createObjectURL(blob)
  this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob));
}
  downloadPdf() {
    const byteCharacters = atob(this.dataService.getPaymentQrPdf());
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: 'application/pdf' });
    const filename = 'filename.pdf';
    saveAs(blob, filename);
  }
}
