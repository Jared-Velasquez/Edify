import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable } from 'rxjs';
import { CourseBasicResponse, CourseBasicUnitResponse } from 'src/app/models/httpResponseModels';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  constructor(private http: HttpClient) { }

  public getCourses(): Observable<CourseBasicUnitResponse[]> {
    return this.http.get<CourseBasicResponse>('https://edify.azurewebsites.net/api/student/courses-basic')
    .pipe(map((res: CourseBasicResponse) => {
      return res.courses;
    }));
  }
}
