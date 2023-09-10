import { Course, Score, User } from "src/app/models/httpResponseModels"

export interface AppState {
    navbar: NavbarState,
    course: CourseState,
    user: UserState,
};

export interface NavbarState {
    expanded: boolean,
};

export interface CourseState {
    scores: Score[],
    courses: Course[],
};

export interface UserState {
    user: User
};