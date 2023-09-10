import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { map, Subscription, switchMap } from 'rxjs';
import { fadeDelayedAnimation, listAnimation } from 'src/app/animations/shared_animations';
import { Course, Module } from 'src/app/models';
import { CourseEmpty, Score } from 'src/app/models/httpResponseModels';
import { CoursesService } from 'src/app/services/courses.service';
import { AppState } from 'src/app/store/models/edifyState';

@Component({
  selector: 'app-modules',
  templateUrl: './modules.component.html',
  styleUrls: ['./modules.component.css'],
  animations: [
    fadeDelayedAnimation,
    listAnimation,
  ],
})
export class ModulesComponent implements OnInit, OnDestroy {
  modules: Module[];
  routeSubscription: Subscription;
  courseId: number;
  isLoading: boolean;
  course: Course;
  scores: Score[];

  constructor(private courseService: CoursesService, private route: ActivatedRoute, private store: Store<AppState>) {
    this.modules = [];
    this.routeSubscription = Subscription.EMPTY;
    this.courseId = 0;
    this.isLoading = true;
    this.course = CourseEmpty;
    this.scores = [];
  }

  ngOnInit(): void {
    this.routeSubscription = this.route.paramMap.pipe(
      switchMap(response => {
        const id: string | null = response.get('id');
        const courseId = id ? parseInt(id) : 0;
        return this.courseService.getModules(courseId).pipe(
          map(modules => ({
            modules,
            courseId,
          }))
        );
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
      switchMap(response => {
        return this.store.select('course').pipe(
          map((course) => ({
            scores: course.scores,
            ...response,
          }))
        );
      }),
      switchMap(response => {
        return this.courseService.getTeacherOfCourse(response.courseId).pipe(
          map(teacher => ({
            teacher,
            ...response,
          }))
        );
      })
    ).subscribe((response) => {
      this.modules = response.modules;
      this.courseId = response.courseId;
      this.course = response.course;
      this.isLoading = false;
      this.scores = response.scores;
    });
  }

  ngOnDestroy(): void {
    if (this.routeSubscription)
      this.routeSubscription.unsubscribe();
  }
}
