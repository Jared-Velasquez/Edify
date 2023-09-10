import { Course, Score, User } from "src/app/models/httpResponseModels"

export interface AppState {
    navbar: NavbarState,
    course: CourseState,
    user: UserState,
};

export interface NavbarState {
    expanded: boolean,
    courses: Course[],
};

export interface CourseState {
    scores: Score[]
};

export interface UserState {
    user: User
};