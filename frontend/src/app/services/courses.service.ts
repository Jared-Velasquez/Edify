import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { AnnouncementResponse, Announcement, CourseResponse, Teacher, Assignment, AssignmentResponse, AssignmentMultipleResponse, Module, ModuleResponse, ScoreResponse } from 'src/app/models/httpResponseModels';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  constructor(private http: HttpClient) { }

  public getCourses(): Observable<CourseResponse> {
    return this.http.get<CourseResponse>('https://edify.azurewebsites.net/api/student/courses');
  }

  public getTeacherOfCourse(teacherId: number): Observable<Teacher> {
    return this.http.get<Teacher>(`https://edify.azurewebsites.net/api/course/teacher/${teacherId}`);
  }

  public getAnnouncements(courseId: number): Observable<Announcement[]> {
    return this.http.get<AnnouncementResponse>(`https://edify.azurewebsites.net/api/course/announcement/${courseId}`)
    .pipe(
      map((res: AnnouncementResponse) => {
        return res.announcements.map((announcement) => ({
          ...announcement,
          createdAt: new Date(announcement.createdAt),
        }));
      }),
      catchError(this.handleGetError),
    );
  }

  public getAssignments(courseId: number): Observable<Assignment[]> {
    return this.http.get<AssignmentMultipleResponse>(`https://edify.azurewebsites.net/api/course/assignments/${courseId}`)
    .pipe(
      map((res: AssignmentMultipleResponse) => {
        return res.assignments.map((assignment) => ({
          ...assignment,
          dueAt: new Date(assignment.dueAt),
          unlockAt: new Date(assignment.unlockAt),
          lockAt: new Date(assignment.lockAt),
          createdAt: new Date(assignment.createdAt),
        }));
      }),
      catchError(this.handleGetError),
    );
  }

  public getAssignment(assignmentId: number): Observable<Assignment> {
    return this.http.get<AssignmentResponse>(`https://edify.azurewebsites.net/api/course/assignment/${assignmentId}`)
    .pipe(
      map((res: AssignmentResponse) => res.assignment),
      catchError(this.handleGetError),
    );
  }

  public getScores(): Observable<ScoreResponse> {
    return this.http.get<ScoreResponse>('https://edify.azurewebsites.net/api/student/assignments');
  }

  public getModules(courseId: number): Observable<Module[]> {
    return this.http.get<ModuleResponse>(`https://edify.azurewebsites.net/api/course/module/${courseId}`)
    .pipe(
      map((res: ModuleResponse) => res.modules),
      catchError(this.handleGetError),
    );
  }

  private handleGetError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('An error occurred:', error.error);
    } else {
      console.error(`Backend returned code ${error.status}, body was: `, error.error);
    }

    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
}
