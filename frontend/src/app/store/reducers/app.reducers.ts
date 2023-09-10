import { courseReducer } from './courses.reducers';
import { navbarReducer } from './navbar.reducers';
import { userReducer } from './user.reducers';

export const appReducer = {
    navbar: navbarReducer,
    course: courseReducer,
    user: userReducer,
};