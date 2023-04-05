import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs'
import {QrRequest} from "./dto/QrRequest";
import {StatementRequest} from "./dto/StatementRequest";
import {InteracValidateRequest} from "./dto/InteracValidateRequest";
import {UserDetailsRequest} from "./dto/UserDetailsRequest";
import {Router} from "@angular/router";
import {LogOutRequest} from "./dto/LogOutRequest";
import {RefreshRequest} from "./dto/RefreshRequest";

@Injectable({
  providedIn: 'root'
})
export class DataService {
  sessionIdStorage = new Map<string,string>();
  verificationImage : any;
  verificationPdf : any;
  paymentQrImage : any;
  paymentQrPdf : any;
  isLoginValid !: boolean;
  isNestedCall !: boolean;
  timeoutId !: number;
  startTime = Date.now();

  private url = "https://www.tbdbank.me/tbd651"

  constructor(private httpClient: HttpClient, private router: Router) {
    this.isLoginValid = false;
    this.isNestedCall = false;

    window.addEventListener('beforeunload', (event) => {
      localStorage.setItem('timeoutState', JSON.stringify({
        timeoutId: this.timeoutId,
        remainingTime: 300000 - (Date.now() - this.startTime)
      }));
    });

    let timeoutState = localStorage.getItem('timeoutState');
    if (timeoutState) {
      timeoutState = JSON.parse(timeoutState);
      this.refreshSession();
      const remainingTime = 300000 - (Date.now() - this.startTime);
      this.timeoutId = setTimeout(() => {
        this.isLoginValid = false;
        localStorage.setItem('sessionId', '');
        if(this.router.url !== "login" && this.router.url !== "registration") {
          this.router.navigate(['login'])
          localStorage.clear();
        }
      }, remainingTime);
    }
  }
  getFirstName() : string {
    return JSON.stringify(localStorage.getItem('firstName'))
  }
  getLastName() : string {
    return JSON.stringify(localStorage.getItem('lastName'))
  }
  getAccountBalance() {
    return Number(JSON.stringify(localStorage.getItem('accountBalance')));
  }
  getEmail() {
    return JSON.stringify(localStorage.getItem('email'))
  }

  setIsLoginValid(isValid:boolean, newSessionId:string) {
    this.isLoginValid = isValid;
    localStorage.setItem('sessionId', newSessionId);
     this.startTime = Date.now();
    this.timeoutId = setTimeout(() => {
      this.isLoginValid = false;
      localStorage.setItem('sessionId', '');
    }, 300000);
  }

  updateSession(isValid:boolean, newSessionId:string) {
    clearTimeout(this.timeoutId);
    this.setIsLoginValid(isValid, newSessionId);
    localStorage.setItem('timeoutState', JSON.stringify({
      timeoutId: this.timeoutId,
      remainingTime: 300000 - (Date.now() - this.startTime)
    }));
  }

  refreshSession() {
    if(!localStorage.getItem('sessionId') && !this.isLoginValid) {
      this.router.navigate(['login'])
    }
    else{
      let refreshRequest = {} as RefreshRequest;
      refreshRequest.sessionId = localStorage.getItem('sessionId') as string
      this.refresh(refreshRequest).subscribe(
        (response) => {
          this.updateSession(true,response);
          this.fetchCustomerDetails();
        },
        error => {
          console.error(error)
        }
      )

    }
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

  validateInterac(validateInteracRequest : InteracValidateRequest):Observable<any> {
    return this.httpClient.post(`${this.url}/transaction/interac/validate`,validateInteracRequest);
  }
  fetchUserDetails(userDetailsRequest : UserDetailsRequest) : Observable<any> {
    return this.httpClient.post(`${this.url}/customer`,userDetailsRequest)
  }

  fetchCustomerDetails()
  {
    if(!localStorage.getItem('sessionId') && !this.isLoginValid) {
      this.router.navigate(['login'])
    }
    else{
      let userDetailsRequest = {} as UserDetailsRequest;
      userDetailsRequest.sessionId = localStorage.getItem('sessionId') as string
      this.fetchUserDetails(userDetailsRequest).subscribe(data => {
        if (data.success) {
          let newSessionId = data.sessionId
          this.updateSession(true,newSessionId)
          localStorage.setItem("accountBalance",JSON.stringify(data.customer.accountBalance))
          localStorage.setItem("email",JSON.stringify(data.customer.email))
        }
        else{
          alert(data.message)
        }
      })
    }
  }

  logOut(logOutRequest : LogOutRequest) {
    return this.httpClient.post(`${this.url}/logout`,logOutRequest,{responseType:"text"})
  }

  refresh(refreshRequest : RefreshRequest) {
    return this.httpClient.post(`${this.url}/refresh`,refreshRequest,{responseType:"text"})
  }
}
