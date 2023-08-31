import { createReducer, on } from '@ngrx/store';
import { expand, collapse, toggle } from '../actions/navbar.actions';
import { AppState, NavbarState } from '../models/edifyState';

export const initialNavbarState: NavbarState = {
    expanded: true,
    courses: []
}

export const navbarReducer = createReducer(
    initialNavbarState,
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