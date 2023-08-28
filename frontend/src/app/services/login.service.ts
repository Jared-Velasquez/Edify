import { TokenResponse, JWTInterface } from 'src/app/models';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';

import { catchError, map, Observable, switchMap } from 'rxjs';
import jwtDecode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(private http: HttpClient) { }

  signup(): void {

  }

  private obtainToken(username: string, password: string): Observable<HttpResponse<TokenResponse>> {
    // Need observe: 'response' to return the full response instead of just the body
    return this.http.post<TokenResponse>('https://edify.azurewebsites.net/api/auth/authenticate', {
      "emailAddress": username,
      "password": password,
    }, {
      observe: 'response',
    });
  }

  login(username: string, password: string): Observable<boolean> {
    /*this.obtainToken(username, password).subscribe({
      next: (response) => {
        if (!response.body)
          return;
        const decodedToken: JWTInterface | undefined = this.getDecodedAccessToken(response.body.token);
        if (!decodedToken)
          return;
        console.log("Decoded token: " + decodedToken);
        this.setSession(decodedToken);
        loginReturn = true;
        console.log("loginReturn in subscribe: " + loginReturn);
        return;
      },
      error: (error) => {
        console.log(error);
        return;
      }
    });
    console.log("Login return out of subscribe: " + loginReturn);*/
    return this.obtainToken(username, password).pipe(
      map((response) => {
        if (!response.body)
          return false;
        const decodedToken: JWTInterface | undefined = this.getDecodedAccessToken(response.body.token);
        if (!decodedToken)
          return false;
        console.log("Decoded token: " + decodedToken);
        this.setSession(decodedToken);
        return true;
      })
    );
  }

  logout() {
    localStorage.removeItem('id_token');
    localStorage.removeItem('expires_at');
  }

  private getDecodedAccessToken(token: string): JWTInterface | undefined {
    try {
      const decoded = jwtDecode<JWTInterface>(token);
      return decoded;
    } catch (exception: any) {
      console.log(exception);
      return undefined;
    }
  }

  private setSession(jwt: JWTInterface) {
    localStorage.setItem('id_token', jwt.id.toString());
    localStorage.setItem('expires_at', jwt.exp.toString());
  }

  isLoggedIn(): boolean {
    const expiration_time: string | null = localStorage.getItem('expires_at');
    console.log(expiration_time);
    if (!expiration_time)
      return false;
    return (parseInt(expiration_time, 10) >= (Date.now() / 1000));
  }
}
