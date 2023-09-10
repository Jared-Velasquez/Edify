import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserResponse } from '../models/httpResponseModels';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public getUser(): Observable<UserResponse> {
    return this.http.get<UserResponse>('https://edify.azurewebsites.net/api/student');
  }
}
