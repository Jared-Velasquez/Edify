import { createReducer, on } from '@ngrx/store';
import { expand, collapse, toggle } from '../actions/navbar.actions';
import { AppState } from '../models/edifyState';

export const initialState = {
    expanded: true,
}

export const navbarReducer = createReducer(
    initialState,
    on(expand, (state) => {
        return {
            ...state,
            expanded: true,
        }
    }),
    on(collapse, (state) => {
        return {
            ...state,
            expanded: false,
        }
    }),
    on(toggle, (state) => {
        return {
            ...state,
            expanded: !state.expanded,
        }
    })
);