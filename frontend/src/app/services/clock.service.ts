import { Injectable } from '@angular/core';
import { interval, mergeMap, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClockService {

  constructor() { }

  getClock(): Observable<Date> {
    return interval(1000).pipe(
      mergeMap(() => of(new Date()))
    );
  }
}
