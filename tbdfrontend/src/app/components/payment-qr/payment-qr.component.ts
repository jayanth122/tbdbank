import {Component, OnInit} from '@angular/core';
import {DataService} from "../../data.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-payment-qr',
  templateUrl: './payment-qr.component.html',
  styleUrls: ['./payment-qr.component.scss']
})
export class PaymentQrComponent implements OnInit {
  public imageUrl : any;
  public pdfUrl:any
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
  downloadPdf(){
    const blob = new Blob([this.dataService.getPaymentQrPdf()], { type: 'application/pdf' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = 'filename.pdf';
    link.click();
  }

}
