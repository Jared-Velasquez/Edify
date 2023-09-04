import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { AnnouncementResponse, Announcement, CourseResponse, Teacher } from 'src/app/models/httpResponseModels';
import { NavLinksInterface } from 'src/constants';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  constructor(private http: HttpClient) { }

  /*public getCourses(): Observable<NavLinksInterface[]> {
    return this.http.get<CourseResponse>('https://edify.azurewebsites.net/api/student/courses')
    .pipe(
      map((res: CourseResponse) => {
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
  }*/

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
