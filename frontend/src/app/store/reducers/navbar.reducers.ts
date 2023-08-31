import { createReducer, on } from '@ngrx/store';
import { expand, collapse, toggle } from '../actions/navbar.actions';
import { AppState } from '../models/edifyState';

export const initialState: AppState = {
    navbar: {
        expanded: true,
    }
}

export const navbarReducer = createReducer(
    initialState,
    on(expand, (state) => {
        return {
            ...state,
            navbar: {
                expanded: true
            }
        }
    }),
    on(collapse, (state) => {
        return {
            ...state,
            navbar: {
                expanded: true
            }
        }
    }),
    on(toggle, (state) => {
        return {
            ...state,
            navbar: {
                expanded: !state.navbar.expanded,
            }
        }
    })
);