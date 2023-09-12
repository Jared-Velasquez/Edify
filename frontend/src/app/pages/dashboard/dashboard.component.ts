import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { listAnimationFast, listAnimationFastReverse } from 'src/app/animations/shared_animations';
import { CalendarDay, Role } from 'src/app/models/edifyModels';
import { Assignment, Course, User, UserEmpty } from 'src/app/models/httpResponseModels';
import { ClockService } from 'src/app/services/clock.service';
import { UserService } from 'src/app/services/user.service';
import { AppState } from 'src/app/store/models/edifyState';
import { dayNumberPickerSuffix, dayPicker, monthPicker, monthPickerShort } from 'src/constants';

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
  userStoreSubscription: Subscription;
  userSubscription: Subscription;
  courseSubscription: Subscription;
  clockSubscription: Subscription;
  user: User;
  role: string;
  textFinished: boolean;
  courses: Course[];
  date: string[];
  week: CalendarDay[];
  assignmentsToday: Assignment[];
  assignmentsShortly: Assignment[];

  constructor(
    private store: Store<AppState>, 
    private clockService: ClockService, 
    private userService: UserService, 
    private router: Router) 
  {
    this.userStoreSubscription = Subscription.EMPTY;
    this.userSubscription = Subscription.EMPTY;
    this.courseSubscription = Subscription.EMPTY;
    this.clockSubscription = Subscription.EMPTY;
    this.user = UserEmpty;
    this.role = "";
    this.textFinished = false;
    this.courses = [];
    this.date = ["", "", ""];
    this.week = [];
    this.assignmentsToday = [];
    this.assignmentsShortly = [];
  }

  ngOnInit() {
    this.userStoreSubscription = this.store.select('user').subscribe((data) => {
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

    this.userSubscription = this.userService.getUserAssignments().subscribe((data) => {
      const today = new Date();
      this.assignmentsToday = data.filter((assignment) => {
        return (today.getFullYear() === assignment.dueAt.getFullYear()) && (today.getMonth() === assignment.dueAt.getMonth()) && (today.getDate() === assignment.dueAt.getDate())
      }).slice(0, 3).sort((x, y) => x.dueAt.getTime() - y.dueAt.getTime());

      this.assignmentsShortly = data.filter((assignment) => {
        return (today.getFullYear() <= assignment.dueAt.getFullYear()) && (today.getMonth() <= assignment.dueAt.getMonth()) && (today.getDate() < assignment.dueAt.getDate())
      }).slice(0, 3).sort((x, y) => x.dueAt.getTime() - y.dueAt.getTime());
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

  convertToDateString(date: Date): string {
    return `${monthPickerShort(date.getMonth())} ${dayNumberPickerSuffix(date.getDate())}, ${date.getFullYear()}`
  }

  convertToTimeString(date: Date): string {
    return date.getHours() + ":" + (date.getMinutes() < 10 ? '0' : '') + date.getMinutes() + ":" + (date.getSeconds() < 10 ? '0' : '') + date.getSeconds();
  }

  onCourseClick(courseId: number) {
    this.router.navigateByUrl(`/courses/${courseId}`);
  }

  onDayClick(day: CalendarDay) {
    this.router.navigateByUrl(`/calendar?year=${day.yearNumber}&month=${day.monthNumber}&day=${day.dayNumber}`);
  }

  onAssignmentClick(assignmentId: number) {
    console.log(assignmentId);
  }

  ngOnDestroy() {
    if (this.userStoreSubscription)
      this.userStoreSubscription.unsubscribe();
    if (this.userSubscription)
      this.userSubscription.unsubscribe();
    if (this.courseSubscription)
      this.courseSubscription.unsubscribe();
    if (this.clockSubscription)
      this.clockSubscription.unsubscribe();
  }
}