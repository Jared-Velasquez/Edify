import { CoursesService } from './../../../services/courses.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { map, of, Subscription, switchMap } from 'rxjs';
import { Course } from 'src/app/models';
import { CourseEmpty, Teacher, TeacherEmpty } from 'src/app/models/httpResponseModels';
import { AppState } from 'src/app/store/models/edifyState';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  routeSubscription: Subscription;
  course: Course;
  courseId: number;
  teacher: Teacher;

  constructor(private store: Store<AppState>, private route: ActivatedRoute, private router: Router, private courseService: CoursesService) {
    this.routeSubscription = Subscription.EMPTY;
    this.course = CourseEmpty;
    this.courseId = 0;
    this.teacher = TeacherEmpty;
  }

  ngOnInit() {
    this.routeSubscription = this.route.paramMap.pipe(
      switchMap(response => {
        const id: string | null = response.get('id');
        const courseId = id ? parseInt(id) : 0;
        return of({
          courseId,
        });
      }),
      switchMap(response => {
        return this.store.select('course').pipe(
          map((courseData) => {
            const courseOptional: Course | undefined = courseData.courses.find(course => course.courseId === response.courseId);
            if (courseOptional) {
              const course: Course = courseOptional;
              return {
                course,
                ...response,
              }
            } else {
              const course: Course = CourseEmpty;
              return {
                course,
                ...response,
              }
            }
          })
        );
      }),
    ).subscribe((response) => {
      this.courseId = response.courseId;
      this.course = response.course;
      console.log(this.course);
    });
  }

  ngOnDestroy() {
    if (this.routeSubscription)
      this.routeSubscription.unsubscribe();
  }
}
