import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Assignment, AssignmentCourseMultipleResponse, AssignmentMultipleResponse, AssignmentResponse, UserResponse } from '../models/httpResponseModels';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public getUser(): Observable<UserResponse> {
    return this.http.get<UserResponse>('https://edify.azurewebsites.net/api/student');
  }

  public getUserAssignments(): Observable<Assignment[]> {
    return this.http.get<AssignmentCourseMultipleResponse>('https://edify.azurewebsites.net/api/student/assignments').pipe(
      map((res: AssignmentCourseMultipleResponse) => {
        return res.assignments.map((assignmentWrapper) => ({
          ...assignmentWrapper.assignment,
          dueAt: new Date(assignmentWrapper.assignment.dueAt),
          unlockAt: new Date(assignmentWrapper.assignment.unlockAt),
          lockAt: new Date(assignmentWrapper.assignment.lockAt),
          createdAt: new Date(assignmentWrapper.assignment.createdAt),
          courseId: assignmentWrapper.courseId,
        }))
      })
    );
  }
}
