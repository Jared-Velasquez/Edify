import { createAction, props } from '@ngrx/store';
import { CourseBasicUnitResponse } from 'src/app/models/httpResponseModels';
import { NavbarActionTypes } from '../models/actionTypes';

export const expand = createAction(NavbarActionTypes.ExpandNavbar);
export const collapse = createAction(NavbarActionTypes.CollapseNavbar);
export const toggle = createAction(NavbarActionTypes.ToggleNavbar);
export const loadCourses = createAction(NavbarActionTypes.LoadCoursesSuccess, props<{ courses: CourseBasicUnitResponse[] }>());
export const loadCoursesError = createAction(NavbarActionTypes.LoadCoursesError);