import { TokenResponse, JWTInterface } from 'src/app/models';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { catchError, map, Observable, switchMap, throwError } from 'rxjs';
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
    return this.obtainToken(username, password).pipe(
      map((response) => {
        if (!response.body)
          return false;
        localStorage.setItem('token', response.body.token);
        const decodedToken: JWTInterface | undefined = this.getDecodedAccessToken(response.body.token);
        if (!decodedToken)
          return false;
        this.setSession(decodedToken);
        return true;
      }),
      catchError(this.handleLoginError),
    );
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('id_token');
    localStorage.removeItem('expires_at');
    localStorage.removeItem('issued_at');
    localStorage.removeItem('audience');
    localStorage.removeItem('issuer');
    localStorage.removeItem('subject');
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
    localStorage.setItem('issued_at', jwt.iat.toString());
    localStorage.setItem('audience', jwt.aud);
    localStorage.setItem('issuer', jwt.iss);
    localStorage.setItem('subject', jwt.sub);
  }

  private handleLoginError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(`Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => error);
  }

  isLoggedIn(): boolean {
    const expiration_time: string | null = localStorage.getItem('expires_at');
    if (!expiration_time) {
      this.logout();
      return false;
    }
    const notExpired = (parseInt(expiration_time, 10) >= (Date.now() / 1000));
    if (!notExpired) {
      this.logout();
      return false;
    }
    return true;
  }

  getToken(): string {
    const token: string | null = localStorage.getItem('token');
    if (!token)
      return "";
    else
      return token;
  }
}
