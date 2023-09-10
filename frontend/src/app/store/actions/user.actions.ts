import { createAction, props } from '@ngrx/store';
import { UserResponse } from 'src/app/models/httpResponseModels';
import { UserActionTypes } from '../models/actionTypes';

export const loadUserSuccess = createAction(UserActionTypes.LoadUserSuccess, props<{ userResponse: UserResponse }>());
export const loadUserError = createAction(UserActionTypes.LoadUserError);