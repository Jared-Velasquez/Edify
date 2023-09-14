import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CalendarDay } from 'src/app/models';
import { dayPicker, dayPickerShort } from 'src/constants';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit, OnDestroy {
  routeSubscription: Subscription;
  selectedDate: Date;
  selectedYear: number;
  selectedMonth: number;
  selectedDay: number;
  week: CalendarDay[];

  constructor(private route: ActivatedRoute, private router: Router) {
    this.routeSubscription = Subscription.EMPTY;
    this.selectedDate = new Date();
    this.selectedYear = 0;
    this.selectedMonth = 0;
    this.selectedDay = 0;
    this.week = []
  }

  ngOnInit() {
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
    for (let i = 0; i < DAYS_BEFORE_TODAY + 1 + DAYS_AFTER_TODAY; i++) {
      var currentDay = new Date(this.selectedDate);
      currentDay.setDate(currentDay.getDate() - 1 + i);
      this.week.push({
        dayTitle: dayPickerShort(currentDay.getDay()),
        dayNumber: currentDay.getDate(),
        monthNumber: currentDay.getMonth(),
        yearNumber: currentDay.getFullYear()
      });
    };

    console.log(this.week);

    // Pad week with one day before yesterday and 5 days after the 5 days after today
    // If the user clicks yesterday, the calendar will show the day before yesterday during the animation
    // If the user clicks 5 days after today, the calendar will show 5 days after the selected date during the animation

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
