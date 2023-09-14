import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { CalendarDay } from 'src/app/models';
import { AppState } from 'src/app/store/models/edifyState';
import { dayPicker, dayPickerShort } from 'src/constants';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit, OnDestroy {
  navbarSubscription: Subscription;
  routeSubscription: Subscription;
  navbarExpanded: boolean;
  selectedDate: Date;
  selectedYear: number;
  selectedMonth: number;
  selectedDay: number;
  week: CalendarDay[];

  constructor(private route: ActivatedRoute, private router: Router, private store: Store<AppState>) {
    this.navbarSubscription = Subscription.EMPTY;
    this.routeSubscription = Subscription.EMPTY;
    this.navbarExpanded = true;
    this.selectedDate = new Date();
    this.selectedYear = 0;
    this.selectedMonth = 0;
    this.selectedDay = 0;
    this.week = []
  }

  ngOnInit() {
    this.navbarSubscription = this.store.select('navbar').subscribe((data) => {
      this.navbarExpanded = data.expanded;
    });

    this.routeSubscription = this.route.queryParamMap.subscribe((params) => {
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
    });
  }

  getCalendarDay(date: Date): CalendarDay {
    return {
      dayTitle: dayPickerShort(date.getDay()),
      dayNumber: date.getDate(),
      monthNumber: date.getMonth(),
      yearNumber: date.getFullYear(),
    };
  }

  onDayClick(day: CalendarDay) {
    const prevDate = this.selectedDate;
    //this.router.navigateByUrl(`/calendar?year=${day.yearNumber}&month=${day.monthNumber}&day=${day.dayNumber}`);
    const nextDate = new Date(day.yearNumber, day.monthNumber, day.dayNumber);
    console.log(this.daysDifference(prevDate, nextDate));
    this.selectedDate = new Date(day.yearNumber, day.monthNumber, day.dayNumber);
    this.generateWeek(this.selectedDate);
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

    console.log(this.week);

  }

  daysDifference(prevDate: Date, nextDate: Date) {
    const day = 1000 * 60 * 60 * 24;

    const prevDateUTC = Date.UTC(prevDate.getFullYear(), prevDate.getMonth(), prevDate.getDate());
    const nextDateUTC = Date.UTC(nextDate.getFullYear(), nextDate.getMonth(), nextDate.getDate());
    return (nextDateUTC - prevDateUTC) / day;
  }

  ngOnDestroy() {
    if (this.routeSubscription)
      this.routeSubscription.unsubscribe();
  }
}
