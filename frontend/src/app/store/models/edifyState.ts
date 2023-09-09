import { Course, Score } from "src/app/models/httpResponseModels"

export interface AppState {
    navbar: NavbarState,
    course: CourseState,
};

export interface NavbarState {
    expanded: boolean,
    courses: Course[],
};

export interface CourseState {
    scores: Score[]
};