import { UserActionTypes } from './../models/actionTypes';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, catchError, switchMap } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';
import { loadUserSuccess } from '../actions/user.actions';

@Injectable()
export class UserEffects {
    constructor(private actions$: Actions, private userService: UserService) {

    }

    loadUser$ = createEffect(() => this.actions$.pipe(
        ofType(UserActionTypes.GetUser),
        switchMap(() => this.userService.getUser()
            .pipe(
                map((user) => {
                    return loadUserSuccess({ userResponse: user })
                }),
                catchError(() => of({ type: UserActionTypes.LoadUserError }))
            )
        )
    ));
}