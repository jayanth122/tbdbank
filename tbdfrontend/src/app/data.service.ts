import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs'
import {QrRequest} from "./dto/QrRequest";
import {StatementRequest} from "./dto/StatementRequest";


@Injectable({
  providedIn: 'root'
})
export class DataService {
  sessionIdStorage = new Map<string,string>();
  verificationImage : any;
  verificationPdf : any;
  paymentQrImage : any;
  paymentQrPdf : any;
  firstName : string;
  lastName : string;
  isLoginValid !: boolean;

  private url = "https://www.tbdbank.me/tbd651"

  constructor(private httpClient: HttpClient) {
    this.firstName = '';
    this.lastName = '';
  }

  setFirstName(firstName:string) {
    this.firstName = firstName;
  }

  setLastName(lastName:string) {
    this.lastName = lastName;
  }

  setIsLoginValid(isValid:boolean, newSessionId:string) {
    this.isLoginValid = isValid;
    localStorage.setItem('sessionId', newSessionId);
      setTimeout(() => {
        this.isLoginValid = false;
        localStorage.setItem('sessionId', '');
      }, 300000);
  }

  sendLoginDetails(loginData:FormData): Observable<any> {
    return this.httpClient.post<any>(`${this.url}/login`,loginData)
  }

  sendRegistrationDetails(registrationData:FormData): Observable<any> {
    return this.httpClient.post(`${this.url}/register`, registrationData)
  }
  sendInteracDetails(interacData:FormData): Observable<any>{
    return this.httpClient.post(`${this.url}/transaction/interac`, interacData)
  }
  setSessionValues(userName:string, value:string){
    this.sessionIdStorage.set(userName,value)
  }
  getSessionValues(userName:string): any {
    if(this.sessionIdStorage.has(userName)){
      return this.sessionIdStorage.get(userName);
    }
    return null
  }
  setVerificationImage(imageData : any) {
    this.verificationImage = imageData;
  }
  getVerificationImage():any {
    return this.verificationImage;
  }
  setVerificationPdf(pdfData:any){
    this.verificationPdf = pdfData;
  }
  getVerificationPdf():any{
    return this.verificationPdf
  }
  setPaymentQrImage(qRImageData:any){
    this.paymentQrImage = qRImageData;
  }
  getPaymentQrImage() : any{
    return this.paymentQrImage;
  }
  setPaymentQrPdf(qrPdfData:any){
    this.paymentQrPdf = qrPdfData;
  }
  getPaymentQrPdf() : any{
    return this.paymentQrPdf;
  }

  getTransactionStatement(statementRequest : StatementRequest): Observable<any> {
    return this.httpClient.post(`${this.url}/transaction/statement`,statementRequest);
  }

  generateQr(qrRequest : QrRequest) : Observable<any> {
    return this.httpClient.post(`${this.url}/qr/generateQR`,qrRequest)
  }

}
