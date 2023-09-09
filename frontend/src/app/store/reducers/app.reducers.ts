import { AppState } from 'src/app/store/models/edifyState';
import { courseReducer } from './courses.reducers';
import { navbarReducer } from './navbar.reducers';

export const appReducer = {
    navbar: navbarReducer,
    course: courseReducer,
};