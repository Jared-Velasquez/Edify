import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(private http: HttpClient) { }

  signup(): void {

  }

  login(username: string, password: string): void {
    console.log('Logging in 2');
    this.http.post('https://edify.azurewebsites.net/api/auth/authenticate', {
      "emailAddress": username,
      "password": password
    }).subscribe((res) => {
      console.log(res);
    });
  }
}
