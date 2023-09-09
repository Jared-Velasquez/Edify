import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { map, Subscription, switchMap } from 'rxjs';
import { Assignment, AssignmentEmpty } from 'src/app/models';
import { Score } from 'src/app/models/httpResponseModels';
import { CoursesService } from 'src/app/services/courses.service';
import { AppState } from 'src/app/store/models/edifyState';

@Component({
  selector: 'app-assignment',
  templateUrl: './assignment-page.component.html',
  styleUrls: ['./assignment-page.component.css']
})
export class AssignmentComponent implements OnInit, OnDestroy {
  @Input() assignment: Assignment;
  routeSubscription: Subscription;
  @Input() assignmentId: number;
  submitTitle: string = "";
  scores: Score[];
  score: number | null;

  constructor(private courseService: CoursesService, private route: ActivatedRoute, private store: Store<AppState>) {
    this.assignment = AssignmentEmpty;
    this.routeSubscription = Subscription.EMPTY;
    this.assignmentId = 0;
    this.submitTitle = "Submit";
    this.scores = [];
    this.score = null;
  }

  ngOnInit() {
    if (this.assignment !== AssignmentEmpty)
      return;
    
    this.routeSubscription = this.route.paramMap.pipe(
      switchMap(response => {
        const id: string | null = response.get('assignmentId');
        const assignmentId = id ? parseInt(id) : 0;
        return this.courseService.getAssignment(assignmentId).pipe(
          map(assignment => ({
            assignment,
            assignmentId,
          }))
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
      this.assignment = response.assignment;
      this.assignmentId = response.assignmentId;
      this.score = this.getScoreOfAssignment(this.assignmentId);
    });
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
