import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs'


@Injectable({
  providedIn: 'root'
})
export class DataService {
  sessionIdStorage = new Map<string,string>();

  private url = "https://www.santhoshprojects.me/tbd651"
  constructor(private httpClient: HttpClient) {
  }
  sendLoginDetails(loginData:FormData): Observable<any> {
    return this.httpClient.post<any>(`${this.url}/login`,loginData)
  }
  sendRegistrationDetails(registrationData:FormData): Observable<any> {
    return this.httpClient.post(`${this.url}/register`, registrationData)
  }
  sendInteracDetails(interacData:FormData): Observable<any>{
    return this.httpClient.post(`${this.url}/interac`, interacData)
  }
  getAccountBalance(customerId: string): Observable<any> {
    return this.httpClient.get<any>(`${this.url}/customers/${customerId}/accountBalance`);
  }
  getCustomerIdByUsername(username: string): Observable<any> {
    return this.httpClient.get<any>(`${this.url}/user-account/${username}`);
  }
  getCustomerById(customerId: number) {
    const url = `${this.url}/customers/${customerId}`;
    return this.httpClient.get<any>(url);
  }
}
