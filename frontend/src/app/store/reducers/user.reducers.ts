import { createReducer, on } from '@ngrx/store';
import { UserEmpty } from 'src/app/models/httpResponseModels';
import { loadUserSuccess, loadUserError } from '../actions/user.actions';
import { UserState } from '../models/edifyState';

export const initialUserState: UserState = {
    user: UserEmpty
};

export const userReducer = createReducer(
    initialUserState,
    on(loadUserSuccess, (state, { userResponse }) => {
        return {
            ...state,
            user: userResponse.user
        };
    }),
    on(loadUserError, (state) => {
        console.log('Loading user error');
        return {
            ...state,
            user: UserEmpty
        };
    }),
)