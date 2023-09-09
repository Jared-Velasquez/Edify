import { createReducer, on } from '@ngrx/store';
import { loadScoresError, loadScoresSuccess } from '../actions/course.actions';
import { CourseState } from '../models/edifyState';

export const initialCourseState: CourseState = {
    scores: []
};

export const courseReducer = createReducer(
    initialCourseState,
    on(loadScoresSuccess, (state, { scoreResponse }) => {
        return {
            ...state,
            scores: scoreResponse.assignments,
        };
    }),
    on(loadScoresError, (state) => {
        console.log('Loading scores error');
        return {
            ...state,
            courses: [],
        };
    })
);