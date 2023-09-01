import { NavbarActionTypes } from './../models/actionTypes';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, mergeMap, catchError } from 'rxjs/operators';
import { CoursesService } from 'src/app/services/courses.service';

@Injectable()
export class CoursesEffects {
    constructor(private actions$: Actions, private courseService: CoursesService) {

    }

    loadCourses$ = createEffect(() => this.actions$.pipe(
        ofType(NavbarActionTypes.SetCourses),
        mergeMap(() => this.courseService.getCourses()
            .pipe(
                map(courses => ({ type: NavbarActionTypes.LoadCoursesSuccess, payload: courses })),
                catchError(() => of({ type: NavbarActionTypes.LoadCoursesError }))
            ))
        )
    );
}