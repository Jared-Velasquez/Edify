import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map, Subscription, switchMap } from 'rxjs';
import { Assignment, AssignmentEmpty } from 'src/app/models';
import { CoursesService } from 'src/app/services/courses.service';

@Component({
  selector: 'app-assignment',
  templateUrl: './assignment.component.html',
  styleUrls: ['./assignment.component.css']
})
export class AssignmentComponent implements OnInit, OnDestroy {
  @Input() assignment: Assignment;
  routeSubscription: Subscription;
  @Input() assignmentId: number;
  submitTitle: string = "";

  constructor(private courseService: CoursesService, private route: ActivatedRoute) {
    this.assignment = AssignmentEmpty;
    this.routeSubscription = Subscription.EMPTY;
    this.assignmentId = 0;
    this.submitTitle = "Submit";
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
    ).subscribe((response) => {
      this.assignment = response.assignment;
      this.assignmentId = response.assignmentId;
    });
  }

  convertDateToString(date: Date): string {
    return date.toLocaleString();
  }

  ngOnDestroy() {
    if (this.routeSubscription)
      this.routeSubscription.unsubscribe();
  }
}
