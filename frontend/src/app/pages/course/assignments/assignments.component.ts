import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { map, Subscription, switchMap } from 'rxjs';
import { fadeDelayedAnimation, listAnimation } from 'src/app/animations/shared_animations';
import { Assignment, Course, DropDownMenuInterface } from 'src/app/models';
import { CourseEmpty, Score } from 'src/app/models/httpResponseModels';
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
  dropDownMenuOptions: DropDownMenuInterface;
  scores: Score[];

  constructor(private courseService: CoursesService, private route: ActivatedRoute, private router: Router, private store: Store<AppState>) {
    this.assignments = [];
    this.routeSubscription = Subscription.EMPTY;
    this.courseId = 0;
    this.isLoading = true;
    this.course = CourseEmpty;
    this.dropDownMenuOptions = {
      title: "Sort By",
      options: [
        "Closest due",
        "Farthest due",
      ],
    };
    this.scores = [];
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
    ).subscribe((response) => {
      this.courseId = response.courseId;
      this.assignments = response.assignments.sort((x, y) => x.dueAt.getTime() - y.dueAt.getTime());
      this.course = response.course;
      this.scores = response.scores;
      this.isLoading = false;
    });
  }

  onAssignmentClick(assignmentId: number) {
    this.router.navigateByUrl(`/courses/${this.courseId}/assignments/${assignmentId}`)
  }

  onChosenSort(sortChosen: string) {
    if (sortChosen === "Farthest due")
      this.assignments = this.assignments.sort((x, y) => x.dueAt.getTime() - y.dueAt.getTime());
    else
      this.assignments = this.assignments.sort((x, y) => y.dueAt.getTime() - x.dueAt.getTime());
  }

  convertDateToString(date: Date): string {
    return date.toLocaleString();
  }

  getScoreOfAssignment(assignmentId: number): number | null {
    let score = null;
    this.scores.forEach((assignment) => {
      if (assignment.assignmentId === assignmentId)
        score = assignment.score;
    });
    return score;
  }

  ngOnDestroy() {
    if (this.routeSubscription)
      this.routeSubscription.unsubscribe();
  }
}
