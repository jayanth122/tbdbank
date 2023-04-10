import {Component, OnInit} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {DataService} from "../../data.service";
import {saveAs} from "file-saver";

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

 // let byteCharacters = new TextDecoder().decode(imgBytes);
  if(imgBytes){
    let byteCharacters = atob(String(imgBytes));
    let byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    let byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: 'image/png' });
    if(URL.createObjectURL(blob)){
      this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob));
    }
  }
}
downloadPdf(){
  const byteCharacters = atob(this.dataService.getVerificationPdf());
  const byteNumbers = new Array(byteCharacters.length);
  for (let i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i);
  }
  const byteArray = new Uint8Array(byteNumbers);
  const blob = new Blob([byteArray], { type: 'application/pdf' });
  const filename = 'Verification.pdf';
  saveAs(blob, filename);
  }

}
