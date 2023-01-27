import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from './login/login-request';
import { Observable } from 'rxjs'
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
    //return this.httpClient.post('http://localhost:8081/tbd651/login', loginRequest)
  }

}
