import { animate, AnimationBuilder, style } from '@angular/animations';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { map, Subscription, switchMap } from 'rxjs';
import { Assignment, CalendarDay } from 'src/app/models';
import { UserService } from 'src/app/services/user.service';
import { AppState } from 'src/app/store/models/edifyState';
import { dayPicker, dayPickerShort } from 'src/constants';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss'],
  animations: [

  ]
})
export class CalendarComponent implements OnInit, OnDestroy {
  routeSubscription: Subscription;
  navbarExpanded: boolean;
  selectedDate: Date;
  selectedYear: number;
  selectedMonth: number;
  selectedDay: number;
  week: CalendarDay[];
  calendarOffset: number;
  isSelectDone: boolean;
  assignments: Assignment[];
  weekAssignments: Assignment[];

  constructor(private route: ActivatedRoute, private animationBuilder: AnimationBuilder, private userService: UserService) {
    this.routeSubscription = Subscription.EMPTY;
    this.navbarExpanded = true;
    this.selectedDate = new Date();
    this.selectedYear = 0;
    this.selectedMonth = 0;
    this.selectedDay = 0;
    this.week = []
    this.calendarOffset = 0;
    this.isSelectDone = true;
    this.assignments = [];
    this.weekAssignments = [];
  }

  ngOnInit() {
    /*this.routeSubscription = this.route.queryParamMap.subscribe((params) => {
      this.selectedYear = Number(params.get('year'));
      this.selectedMonth = Number(params.get('month'));
      this.selectedDay = Number(params.get('day'));

      //console.log(`${this.selectedYear} ${this.selectedMonth} ${this.selectedDay}`);

      // Select date to display
      if (this.selectedYear && this.selectedMonth && this.selectedDay)
        this.selectedDate = new Date(this.selectedYear, this.selectedMonth, this.selectedDay);
      else 
        this.selectedDate = new Date();

      this.generateWeek(this.selectedDate);
    });*/
    this.routeSubscription = this.route.queryParamMap.pipe(
      switchMap(response => {
        return this.userService.getUserAssignments().pipe(
          map(assignments => ({
            assignments,
            ...response,
          }))
        );
      })
    ).subscribe(response => {
      this.selectedYear = Number(response.get('year'));
      this.selectedMonth = Number(response.get('month'));
      this.selectedDay = Number(response.get('day'));
      this.assignments = response.assignments;

      console.log(`${this.selectedYear} ${this.selectedMonth} ${this.selectedDay}`);

      // Select date to display
      if (this.selectedYear && this.selectedMonth && this.selectedDay)
        this.selectedDate = new Date(this.selectedYear, this.selectedMonth, this.selectedDay);
      else 
        this.selectedDate = new Date();

      this.getAssignmentsToday();
      this.generateWeek(this.selectedDate);
    })
  }

  getCalendarDay(date: Date): CalendarDay {
    return {
      dayTitle: dayPickerShort(date.getDay()),
      dayNumber: date.getDate(),
      monthNumber: date.getMonth(),
      yearNumber: date.getFullYear(),
    };
  }

  onDayClick(day: CalendarDay, calendarBody: any) {
    if (!this.isSelectDone)
      return;
    this.isSelectDone = false;
    const prevDate = this.selectedDate;
    const nextDate = this.calendarDayToDate(day);
    const difference = this.daysDifference(prevDate, nextDate)
    console.log(difference);
    //this.selectedDate = new Date(day.yearNumber, day.monthNumber, day.dayNumber);
    const animation = this.animationBuilder.build([
      style({
        transform: `translateX(${this.calendarOffset}%)`,
        mixBlendMode: 'difference',
      }),
      animate('350ms ease-in-out', style({
        transform: `translateX(${this.calendarOffset + (-1 * difference * 14.2857)}%)`,
        mixBlendMode: 'difference',
      }))
    ]);

    // If difference is positive, add to end of the week array
    if (difference >= 1) {
      for (let i = 0; i < difference; i++) {
        var currentDay = new Date(this.calendarDayToDate(this.week[this.week.length - difference - i]));
        currentDay.setDate(currentDay.getDate() + difference + i);
        this.week.push({
          dayTitle: dayPickerShort(currentDay.getDay()),
          dayNumber: currentDay.getDate(),
          monthNumber: currentDay.getMonth(),
          yearNumber: currentDay.getFullYear()
        });
      };
    }

    const player = animation.create(calendarBody);
    player.play();
    player.onDone(() => {
      if (difference < 0 && day === this.week[0]) {
        var currentDay = new Date(nextDate);
        currentDay.setDate(currentDay.getDate() - 1);
        this.week.unshift({
          dayTitle: dayPickerShort(currentDay.getDay()),
          dayNumber: currentDay.getDate(),
          monthNumber: currentDay.getMonth(),
          yearNumber: currentDay.getFullYear()
        });
      }
      
      this.selectedDate = nextDate;
      this.calendarOffset += -1 * difference * 14.2857;
      this.isSelectDone = true;
    })
  }

  generateId(day: CalendarDay): string {
    return `${day.yearNumber}-${day.monthNumber}-${day.dayNumber}`
  }

  generateWeek(selectedDate: Date) {
    // Clear week array if there are already days stored
    this.week = [];

    const DAYS_BEFORE_TODAY = 1;
    const DAYS_AFTER_TODAY = 5;
      
    // Generate seven CalendarDays: yesterday, today, and the five days after today

    // Pad week with one day before yesterday
    // Moving backwards on the calendar is a problem because an element cannot be inserted to the start
    // of an array with an animation; the movement would be jarring
    for (let i = 0; i < (DAYS_BEFORE_TODAY * 2) + 1 + DAYS_AFTER_TODAY; i++) {
      var currentDay = new Date(this.selectedDate);
      currentDay.setDate(currentDay.getDate() - (DAYS_BEFORE_TODAY * 2) + i);
      this.week.push({
        dayTitle: dayPickerShort(currentDay.getDay()),
        dayNumber: currentDay.getDate(),
        monthNumber: currentDay.getMonth(),
        yearNumber: currentDay.getFullYear()
      });
    };
  }

  getAssignmentsToday() {
    const startDate: Date = new Date(Date.UTC(this.selectedDate.getFullYear(), this.selectedDate.getMonth(), this.selectedDate.getDate(), 0, 0, 0));
    const endDate: Date = new Date(Date.UTC(this.selectedDate.getFullYear(), this.selectedDate.getMonth(), this.selectedDate.getDate(), 23, 59, 59));
    console.log(startDate);
    console.log(endDate);
    this.weekAssignments = this.assignments.filter((assignment) => startDate.getTime() <= assignment.dueAt.getTime() && assignment.dueAt.getTime() <= endDate.getTime());
  }

  daysDifference(prevDate: Date, nextDate: Date) {
    const day = 1000 * 60 * 60 * 24;

    const prevDateUTC = Date.UTC(prevDate.getFullYear(), prevDate.getMonth(), prevDate.getDate());
    const nextDateUTC = Date.UTC(nextDate.getFullYear(), nextDate.getMonth(), nextDate.getDate());
    return (nextDateUTC - prevDateUTC) / day;
  }

  calendarDayToDate(day: CalendarDay): Date {
    return new Date(day.yearNumber, day.monthNumber, day.dayNumber);
  }

  ngOnDestroy() {
    if (this.routeSubscription)
      this.routeSubscription.unsubscribe();
  }
}
