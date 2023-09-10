export enum NavbarActionTypes {
    ExpandNavbar = '[Navbar] expand',
    CollapseNavbar = '[Navbar] collapse',
    ToggleNavbar = '[Navbar] toggle',
};

export enum CourseActionTypes {
    GetCourses = '[Courses] courses',
    LoadCoursesSuccess = '[Courses] Courses Loaded Success',
    LoadCoursesError = '[Courses] Courses Loaded Error',
    GetScores = '[Courses] scores',
    LoadScoresSuccess = '[Courses] Scores Loaded Success',
    LoadScoresError = '[Courses] Scores Loaded Error',
};

export enum UserActionTypes {
    GetUser = '[User] user',
    LoadUserSuccess = '[User] User Loaded Success',
    LoadUserError = '[User] User Loaded Error',
};