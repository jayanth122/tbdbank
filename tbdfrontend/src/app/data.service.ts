import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs'
import {Transaction} from "./dto/Transaction";



@Injectable({
  providedIn: 'root'
})
export class DataService {
  sessionIdStorage = new Map<string,string>();
  // private url = "http://132.145.103.186:8081/tbd651"
  private url = "http://localhost:8081/tbd651"
  constructor(private httpClient: HttpClient) {
  }
  sendLoginDetails(loginData:FormData): Observable<any> {
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
    return this.httpClient.post<Transaction[]>(`${this.url}/transaction/statement`,transactionForm);
  }
  generateQr(customerId : string) : Observable<any> {
    return this.httpClient.post(`${this.url}/qr/generateQR`,customerId)
  }

}
