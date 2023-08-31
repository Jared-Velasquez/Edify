import { CourseBasicUnitResponse } from "src/app/models/httpResponseModels"

export interface AppState {
    navbar: NavbarState
};

export interface NavbarState {
    expanded: boolean,
    courses: CourseBasicUnitResponse[],
}