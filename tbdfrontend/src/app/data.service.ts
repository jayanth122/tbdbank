import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from './login/login-request';
import { Observable } from 'rxjs'
import {RegRequest} from "./registration/regRequest";
@Injectable({
  providedIn: 'root'
})
export class DataService {

  private url = "http://localhost:8081/tbd651"
  constructor(private httpClient: HttpClient) {
  }
  sendLoginDetails(loginRequest: LoginRequest): Observable<any> {
    console.log(loginRequest)
    return this.httpClient.post(`${this.url}/login`, loginRequest)
  }
  sendRegistrationDetails(regRequest: RegRequest): Observable<any> {
    console.log(regRequest)
    return this.httpClient.post(`${this.url}/registration`, regRequest)

  }

}
