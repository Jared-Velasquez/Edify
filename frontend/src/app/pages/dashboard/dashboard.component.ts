import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { listAnimationFast, listAnimationFastReverse } from 'src/app/animations/shared_animations';
import { CalendarDay, Role } from 'src/app/models/edifyModels';
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
  week: CalendarDay[];

  constructor(private store: Store<AppState>, private clockService: ClockService, private router: Router) {
    this.userSubscription = Subscription.EMPTY;
    this.courseSubscription = Subscription.EMPTY;
    this.clockSubscription = Subscription.EMPTY;
    this.user = UserEmpty;
    this.role = "";
    this.textFinished = false;
    this.courses = [];
    this.date = ["", "", ""];
    this.week = [];
  }

  ngOnInit() {
    this.userSubscription = this.store.select('user').subscribe((data) => {
      this.user = data.user;
    });

    this.courseSubscription = this.store.select('course').subscribe((data) => {
      this.courses = data.courses;
    });

    this.clockSubscription = this.clockService.getClock().subscribe((data) => {
      this.date[0] = dayPicker(data.getDay());
      this.date[1] = monthPicker(data.getMonth()) + " " + data.getDate() + ", " + data.getFullYear();
      this.date[2] = data.getHours() + ":" + (data.getMinutes() < 10 ? '0' : '') + data.getMinutes() + ":" + (data.getSeconds() < 10 ? '0' : '') + data.getSeconds();
    });

    // Generate seven CalendarDays: yesterday, today, and the five days after today
    for (let i = 0; i < 7; i++) {
      let currentDay = new Date();
      currentDay.setDate(currentDay.getDate() - 1 + i);
      this.week.push({
        dayTitle: dayPicker(currentDay.getDay()),
        dayNumber: currentDay.getDate(),
        monthNumber: currentDay.getMonth(),
        yearNumber: currentDay.getFullYear()
      });
    }
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

  onDayClick(day: CalendarDay) {
    this.router.navigateByUrl(`/calendar?year=${day.yearNumber}&month=${day.monthNumber}&day=${day.dayNumber}`);
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