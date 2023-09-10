import { createReducer, on } from '@ngrx/store';
import { loadCoursesError, loadCoursesSuccess, loadScoresError, loadScoresSuccess } from '../actions/course.actions';
import { CourseState } from '../models/edifyState';

export const initialCourseState: CourseState = {
    scores: [],
    courses: [],
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