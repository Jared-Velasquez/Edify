import { createReducer, on } from '@ngrx/store';
import { expand, collapse } from '../actions/navbar.actions';

export const initialStateNavbar = true;

export const navbarReducer = createReducer(
    initialStateNavbar,
    on(expand, (state) => true),
    on(collapse, (state) => false),
)