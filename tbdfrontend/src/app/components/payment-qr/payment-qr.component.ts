import {Component, OnInit} from '@angular/core';
import {DataService} from "../../data.service";
import {DomSanitizer} from "@angular/platform-browser";
import { saveAs } from 'file-saver'

@Component({
  selector: 'app-payment-qr',
  templateUrl: './payment-qr.component.html',
  styleUrls: ['./payment-qr.component.scss']
})
export class PaymentQrComponent implements OnInit {
  public imageUrl : any;
  constructor(private dataService:DataService,private sanitizer:DomSanitizer){}
ngOnInit() {
  let imgBytes = this.dataService.getPaymentQrImage();
  let byteCharacters = atob(imgBytes);
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
