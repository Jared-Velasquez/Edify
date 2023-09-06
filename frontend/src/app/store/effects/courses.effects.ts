import { NavbarActionTypes } from './../models/actionTypes';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { EMPTY, of } from 'rxjs';
import { map, mergeMap, exhaustMap, catchError, switchMap } from 'rxjs/operators';
import { CoursesService } from 'src/app/services/courses.service';
import { loadCourses } from '../actions/navbar.actions';

@Injectable()
export class CoursesEffects {
    constructor(private actions$: Actions, private courseService: CoursesService) {

    }

    loadCourses$ = createEffect(() => this.actions$.pipe(
        ofType(NavbarActionTypes.SetCourses),
        switchMap(() => this.courseService.getCourses()
            .pipe(
                map((courses) => loadCourses({courseResponse: courses})),
                catchError(() => of({ type: NavbarActionTypes.LoadCoursesError }))
            ))
        )
    );
}