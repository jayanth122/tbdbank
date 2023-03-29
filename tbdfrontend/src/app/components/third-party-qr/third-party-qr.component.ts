import {Component, OnInit} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {DataService} from "../../data.service";

@Component({
  selector: 'app-third-party-qr',
  templateUrl: './third-party-qr.component.html',
  styleUrls: ['./third-party-qr.component.scss']
})
export class ThirdPartyQrComponent implements OnInit{
  public imageUrl : any;
  constructor(private sanitizer:DomSanitizer, private dataService:DataService) {
  }
ngOnInit() {
  let imgBytes = this.dataService.getVerificationImage();
  let byteCharacters = atob(imgBytes);
  let byteNumbers = new Array(byteCharacters.length);
  for (let i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i);
  }
  let byteArray = new Uint8Array(byteNumbers);
  const blob = new Blob([byteArray], { type: 'image/png' });
  console.log(URL.createObjectURL(blob));
  this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob));
}
downloadPdf(){
  const blob = new Blob([this.dataService.getVerificationPdf()], { type: 'application/pdf' });
  const url = URL.createObjectURL(blob);
  const link = document.createElement('a');
  link.href = url;
  link.download = 'filename.pdf';
  link.click();
  }

}
