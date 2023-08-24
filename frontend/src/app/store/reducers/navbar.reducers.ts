import { createReducer, on } from '@ngrx/store';
import { expand, collapse } from '../actions/navbar.actions';
import { AppState } from '../models/edifyState';

export const initialState: AppState = {
    expanded: true
}

export const navbarReducer = createReducer(
    initialState,
    on(expand, (state) => ({ ...state, expanded: true})),
    on(collapse, (state) => ({ ...state, expanded: false})),
)