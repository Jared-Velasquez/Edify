import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { listAnimationFast, listAnimationFastReverse } from 'src/app/animations/shared_animations';
import { Role } from 'src/app/models/edifyModels';
import { Course, User, UserEmpty } from 'src/app/models/httpResponseModels';
import { ClockService } from 'src/app/services/clock.service';
import { AppState } from 'src/app/store/models/edifyState';
import { dayPicker, monthPicker } from 'src/constants';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  animations: [
    listAnimationFast,
    listAnimationFastReverse,
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
  clockSubscription: Subscription;
  user: User;
  role: string;
  textFinished: boolean;
  courses: Course[];
  date: string[];

  constructor(private store: Store<AppState>, private clockService: ClockService, private router: Router) {
    this.userSubscription = Subscription.EMPTY;
    this.courseSubscription = Subscription.EMPTY;
    this.clockSubscription = Subscription.EMPTY;
    this.user = UserEmpty;
    this.role = "";
    this.textFinished = false;
    this.courses = [];
    this.date = ["", "", ""];
  }

  ngOnInit() {
    this.userSubscription = this.store.select('user').subscribe((data) => {
      this.user = data.user;
    });

    this.courseSubscription = this.store.select('course').subscribe((data) => {
      this.courses = data.courses;
    })

    this.clockSubscription = this.clockService.getClock().subscribe((data) => {
      console.log(data);
      this.date[0] = dayPicker(data.getDay());
      this.date[1] = monthPicker(data.getMonth()) + " " + data.getDate() + ", " + data.getFullYear();
      this.date[2] = data.getHours() + ":" + data.getMinutes() + ":" + (data.getSeconds() < 10 ? '0' : '') + data.getSeconds();
    })
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

  onCourseClick(courseId: number) {
    this.router.navigateByUrl(`/courses/${courseId}`);
  }

  ngOnDestroy() {
    if (this.userSubscription)
      this.userSubscription.unsubscribe();
    if (this.courseSubscription)
      this.courseSubscription.unsubscribe();
    if (this.clockSubscription)
      this.clockSubscription.unsubscribe();
  }
}