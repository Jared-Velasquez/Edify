import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { CourseBasicResponse, CourseBasicUnitResponse } from 'src/app/models/httpResponseModels';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  constructor(private http: HttpClient) { }

  public getCourses(): Observable<CourseBasicUnitResponse[]> {
    return this.http.get<CourseBasicResponse>('https://edify.azurewebsites.net/api/student/courses-basic')
    .pipe(
      map((res: CourseBasicResponse) => {
        return res.courses;
      }),
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
