import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Assignment, AssignmentMultipleResponse, AssignmentResponse, UserResponse } from '../models/httpResponseModels';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public getUser(): Observable<UserResponse> {
    return this.http.get<UserResponse>('https://edify.azurewebsites.net/api/student');
  }

  public getUserAssignments(): Observable<Assignment[]> {
    return this.http.get<AssignmentMultipleResponse>('https://edify.azurewebsites.net/api/student/assignments').pipe(
      map((res: AssignmentMultipleResponse) => {
        return res.assignments.map((assignment) => ({
          ...assignment,
          dueAt: new Date(assignment.dueAt),
          unlockAt: new Date(assignment.unlockAt),
          lockAt: new Date(assignment.lockAt),
          createdAt: new Date(assignment.createdAt),
        }));
      })
    );
  }
}
