import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { AnnouncementResponse, AnnouncementUnitResponse, CourseBasicResponse, CourseBasicUnitResponse } from 'src/app/models/httpResponseModels';
import { NavLinksInterface } from 'src/constants';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  constructor(private http: HttpClient) { }

  public getCourses(): Observable<NavLinksInterface[]> {
    return this.http.get<CourseBasicResponse>('https://edify.azurewebsites.net/api/student/courses-basic')
    .pipe(
      map((res: CourseBasicResponse) => {
        const coursesNavlinks: NavLinksInterface[] = [];
        res.courses.forEach((course) => {
          coursesNavlinks.push({
            name: course.title,
            link: `course/${course.courseId}`,
            icon: 'fal fa-pencil',
            subitems: [
              {
                link: `courses/${course.courseId}/home`,
                name: "Home",
              },
              {
                link: `courses/${course.courseId}/announcements`,
                name: "Announcements",
              },
              {
                link: `courses/${course.courseId}/modules`,
                name: "Modules",
              },
              {
                link: `courses/${course.courseId}/assignments`,
                name: "Assignments",
              },
            ]
          })
        })
        return coursesNavlinks;
      }),
      catchError(this.handleGetError),
    );
  }

  public getAnnouncements(courseId: number): Observable<AnnouncementUnitResponse[]> {
    return this.http.get<AnnouncementResponse>(`https://edify.azurewebsites.net/api/course/announcement/${courseId}`)
    .pipe(
      map((res: AnnouncementResponse) => {
        return res.announcements;
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
