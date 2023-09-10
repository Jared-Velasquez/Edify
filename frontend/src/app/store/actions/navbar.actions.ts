import { createAction, props } from '@ngrx/store';
import { NavbarActionTypes } from '../models/actionTypes';

export const expand = createAction(NavbarActionTypes.ExpandNavbar);
export const collapse = createAction(NavbarActionTypes.CollapseNavbar);
export const toggle = createAction(NavbarActionTypes.ToggleNavbar);