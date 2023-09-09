import { CourseActionTypes, NavbarActionTypes } from './../models/actionTypes';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { EMPTY, of } from 'rxjs';
import { map, mergeMap, exhaustMap, catchError, switchMap } from 'rxjs/operators';
import { CoursesService } from 'src/app/services/courses.service';
import { loadCoursesSuccess } from '../actions/navbar.actions';
import { loadScoresSuccess } from '../actions/course.actions';

@Injectable()
export class CoursesEffects {
    constructor(private actions$: Actions, private courseService: CoursesService) {

    }

    loadCourses$ = createEffect(() => this.actions$.pipe(
        ofType(NavbarActionTypes.GetCourses),
        switchMap(() => this.courseService.getCourses()
            .pipe(
                map((courses) => loadCoursesSuccess({courseResponse: courses})),
                catchError(() => of({ type: NavbarActionTypes.LoadCoursesError }))
            ))
        )
    );

    loadScores$ = createEffect(() => this.actions$.pipe(
        ofType(CourseActionTypes.GetScores),
        switchMap(() => this.courseService.getScores()
            .pipe(
                map((scores) => loadScoresSuccess({scoreResponse: scores})),
                catchError(() => of({ type: CourseActionTypes.LoadScoresError }))
            ))
        )
    );
}