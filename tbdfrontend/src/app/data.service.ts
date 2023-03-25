import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs'
import {Transaction} from "./dto/Transaction";
import {QrRequest} from "./dto/QrRequest";



@Injectable({
  providedIn: 'root'
})
export class DataService {
  sessionIdStorage = new Map<string,string>();
  private url = "http://132.145.103.186/tbd651"
  // private url = "http://localhost:8081/tbd651"
  constructor(private httpClient: HttpClient) {
  }
  sendLoginDetails(loginData:FormData): Observable<any> {
    let headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*'); // create header object
    headers = headers.append('Access-Control-Allow-Methods','GET, POST, PATCH, PUT, DELETE, OPTIONS'); // add a new header, creating a new object
    headers = headers.append('Access-Control-Allow-Headers', 'Origin, Content-Type, X-Auth-Token');
    return this.httpClient.post(`${this.url}/login`,loginData)
  }
  sendRegistrationDetails(registrationData:FormData): Observable<any> {
    return this.httpClient.post(`${this.url}/register`, registrationData)
  }
  setSessionValues(userName:string, value:string){
    this.sessionIdStorage.set(userName,value)
  }
  getSessionValues(userName:string): any {
    if(this.sessionIdStorage.has(userName)){
      return this.sessionIdStorage.get(userName);
    }
    return ""
  }
  getTransactionStatement(transactionForm : FormData): Observable<any> {
    return this.httpClient.post(`${this.url}/transaction/statement`,transactionForm);
  }
  generateQr(qrRequest : QrRequest) : Observable<any> {
    let headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    return this.httpClient.post(`${this.url}/qr/generateQR`,qrRequest,{headers:headers, withCredentials:true})
  }

}
