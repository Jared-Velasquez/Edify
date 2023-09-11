import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { Role } from 'src/app/models/edifyModels';
import { Course, User, UserEmpty } from 'src/app/models/httpResponseModels';
import { CoursesService } from 'src/app/services/courses.service';
import { AppState } from 'src/app/store/models/edifyState';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  animations: [
    trigger('fade', [
      transition(':enter', [
        style({
          opacity: 0,
        }),
        animate('0.25s', style({
          opacity: 1,
        })),
      ])
    ]),
  ]
})
export class DashboardComponent implements OnInit, OnDestroy {
  userSubscription: Subscription;
  courseSubscription: Subscription;
  user: User;
  role: string;
  textFinished: boolean;
  courses: Course[];

  constructor(private store: Store<AppState>) {
    this.userSubscription = Subscription.EMPTY;
    this.courseSubscription = Subscription.EMPTY;
    this.user = UserEmpty;
    this.role = "";
    this.textFinished = false;
    this.courses = [];
  }

  ngOnInit() {
    this.userSubscription = this.store.select('user').subscribe((data) => {
      this.user = data.user;
    });

    this.courseSubscription = this.store.select('course').subscribe((data) => {
      this.courses = data.courses;
    })
  }

  ngOnDestroy() {
    if (this.userSubscription)
      this.userSubscription.unsubscribe();
    if (this.courseSubscription)
      this.courseSubscription.unsubscribe();
  }

  getRole(role: string): string {
    if (Role.TEACHER === role)
      return "Teacher";
    else
      return "Student";
  }

  onTextFinished(event: boolean) {
    if (event) {
      console.log("Text finished")
      this.textFinished = true;
    }
  }
}