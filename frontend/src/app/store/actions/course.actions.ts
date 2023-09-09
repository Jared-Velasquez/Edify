import { createAction, props } from '@ngrx/store';
import { ScoreResponse } from 'src/app/models/httpResponseModels';
import { CourseActionTypes} from '../models/actionTypes';

export const loadScoresSuccess = createAction(CourseActionTypes.LoadScoresSuccess, props<{ scoreResponse: ScoreResponse}>());
export const loadScoresError = createAction(CourseActionTypes.LoadScoresError);