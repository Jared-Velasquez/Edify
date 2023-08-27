import { createAction } from '@ngrx/store';
import { ActionTypes } from '../models/actionTypes';

export const expand = createAction('expand');
export const collapse = createAction('collapse');
export const toggle = createAction('toggle');