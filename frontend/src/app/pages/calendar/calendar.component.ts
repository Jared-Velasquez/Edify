import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { CalendarDay } from 'src/app/models';
import { dayPicker, dayPickerShort } from 'src/constants';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit, OnDestroy {
  routeSubscription: Subscription;
  selectedDate: Date;
  selectedYear: number;
  selectedMonth: number;
  selectedDay: number;
  week: CalendarDay[];

  constructor(private route: ActivatedRoute) {
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

      // Select date to display
      if (this.selectedYear && this.selectedMonth && this.selectedDay)
        this.selectedDate = new Date(this.selectedYear, this.selectedMonth, this.selectedDay);
      else 
        this.selectedDate = new Date();
      
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
      };

      console.log(this.week);
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

  ngOnDestroy() {
    if (this.routeSubscription)
      this.routeSubscription.unsubscribe();
  }
}
