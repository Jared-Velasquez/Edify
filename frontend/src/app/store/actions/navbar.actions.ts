import { createAction, props } from '@ngrx/store';
import { CourseResponse } from 'src/app/models/httpResponseModels';
import { NavbarActionTypes } from '../models/actionTypes';

export const expand = createAction(NavbarActionTypes.ExpandNavbar);
export const collapse = createAction(NavbarActionTypes.CollapseNavbar);
export const toggle = createAction(NavbarActionTypes.ToggleNavbar);
export const loadCoursesSuccess = createAction(NavbarActionTypes.LoadCoursesSuccess, props<{ courseResponse: CourseResponse}>());
export const loadCoursesError = createAction(NavbarActionTypes.LoadCoursesError);