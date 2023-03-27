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
    return this.httpClient.post(`${this.url}/login`,loginData)
  }
  sendRegistrationDetails(registrationData:FormData): Observable<any> {
    return this.httpClient.post(`${this.url}/register`, registrationData)
  }

}
