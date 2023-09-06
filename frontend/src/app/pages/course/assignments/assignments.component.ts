import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { map, Subscription, switchMap } from 'rxjs';
import { fadeDelayedAnimation, listAnimation } from 'src/app/animations/shared_animations';
import { Assignment, Course } from 'src/app/models';
import { CourseEmpty } from 'src/app/models/httpResponseModels';
import { CoursesService } from 'src/app/services/courses.service';
import { AppState } from 'src/app/store/models/edifyState';

@Component({
  selector: 'app-assignments',
  templateUrl: './assignments.component.html',
  styleUrls: ['./assignments.component.css'],
  animations: [
    fadeDelayedAnimation,
    listAnimation,
  ],
})
export class AssignmentsComponent implements OnInit, OnDestroy {
  assignments: Assignment[];
  routeSubscription: Subscription;
  courseId: number;
  isLoading: boolean;
  course: Course;

  constructor(private courseService: CoursesService, private route: ActivatedRoute, private store: Store<AppState>) {
    this.assignments = [];
    this.routeSubscription = Subscription.EMPTY;
    this.courseId = 0;
    this.isLoading = true;
    this.course = CourseEmpty;
  }

  ngOnInit() {
    this.routeSubscription = this.route.paramMap.pipe(
      switchMap(response => {
        const id: string | null = response.get('id');
        const courseId = id ? parseInt(id) : 0;
        return this.courseService.getAssignments(courseId).pipe(
          map(assignments => ({
            assignments,
            courseId,
          }))
        );
      }),
      switchMap(response => {
        return this.store.select('navbar').pipe(
          map((navbarData) => {
            const courseOptional: Course | undefined = navbarData.courses.find(course => course.courseId === response.courseId);
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
      this.assignments = response.assignments.sort((x, y) => x.dueAt.getTime() - y.dueAt.getTime());
      this.course = response.course;
    });
  }

  ngOnDestroy() {
    if (this.routeSubscription)
      this.routeSubscription.unsubscribe();
  }
}
