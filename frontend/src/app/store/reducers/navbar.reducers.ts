import { createReducer, on } from '@ngrx/store';
import { expand, collapse, toggle, loadCoursesError, loadCoursesSuccess } from '../actions/navbar.actions';
import { NavbarState } from '../models/edifyState';

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
        };
    }),
    on(collapse, (state) => {
        return {
            ...state,
            expanded: false,
        };
    }),
    on(toggle, (state) => {
        return {
            ...state,
            expanded: !state.expanded,
        };
    }),
    on(loadCoursesSuccess, (state, { courseResponse }) => {
        return {
            ...state,
            courses: courseResponse.courses,
        };
    }),
    on(loadCoursesError, (state) => {
        console.log('Loading courses error');
        return {
            ...state,
            courses: [],
        };
    })
);