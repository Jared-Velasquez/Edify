import { createAction } from '@ngrx/store';
import { ActionTypes } from '../models/actionTypes';

export const expand = createAction(ActionTypes.ExpandNavbar);
export const collapse = createAction(ActionTypes.CollapseNavbar);
export const toggle = createAction(ActionTypes.ToggleNavbar);