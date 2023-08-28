import { TokenResponse } from './../models/httpresponses';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(private http: HttpClient) { }

  signup(): void {

  }

  login(username: string, password: string): Observable<HttpResponse<TokenResponse>> {
    // Need observe: 'response' to return the full response instead of just the body
    return this.http.post<TokenResponse>('https://edify.azurewebsites.net/api/auth/authenticate', {
      "emailAddress": username,
      "password": password,
    }, {
      observe: 'response',
    });
  }
}
