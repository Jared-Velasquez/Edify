import { createAction, props } from '@ngrx/store';
import { CourseResponse, ScoreResponse } from 'src/app/models/httpResponseModels';
import { CourseActionTypes} from '../models/actionTypes';

export const loadCoursesSuccess = createAction(CourseActionTypes.LoadCoursesSuccess, props<{ courseResponse: CourseResponse}>());
export const loadCoursesError = createAction(CourseActionTypes.LoadCoursesError);
export const loadScoresSuccess = createAction(CourseActionTypes.LoadScoresSuccess, props<{ scoreResponse: ScoreResponse}>());
export const loadScoresError = createAction(CourseActionTypes.LoadScoresError);