import { CoursesService } from './../../../services/courses.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { map, of, Subscription, switchMap } from 'rxjs';
import { Course } from 'src/app/models';
import { CourseEmpty, Module, Teacher, TeacherEmpty } from 'src/app/models/httpResponseModels';
import { AppState } from 'src/app/store/models/edifyState';
import { Position } from 'src/app/models/edifyModels';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  routeSubscription: Subscription;
  course: Course;
  courseId: number;
  modules: Module[];

  constructor(private store: Store<AppState>, private route: ActivatedRoute, private router: Router, private courseService: CoursesService) {
    this.routeSubscription = Subscription.EMPTY;
    this.course = CourseEmpty;
    this.courseId = 0;
    this.modules = [];
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
                modules: course.modules,
                ...response,
              }
            } else {
              const course: Course = CourseEmpty;
              return {
                course,
                modules: [],
                ...response,
              }
            }
          })
        );
      }),
    ).subscribe((response) => {
      this.courseId = response.courseId;
      this.course = response.course;
      this.modules = response.modules ? response.modules : [];
      console.log(this.course);
    });
  }

  positionPicker(position: string): string {
    if (position == Position.TEACHING_PROFESSOR)
        return "Teaching Professor";
    else if (position == Position.LECTURER)
        return "Lecturer";
    else if (position == Position.ADJUNCT_PROFESSOR)
        return "Adjunct Professor";
    else if (position == Position.ASSISTANT_PROFESSOR)
        return "Assistant Professor";
    else
        return "Professor";
  }

  onModuleClick() {
    this.router.navigateByUrl(`/courses/${this.courseId}/modules`);
  }

  ngOnDestroy() {
    if (this.routeSubscription)
      this.routeSubscription.unsubscribe();
  }
}
