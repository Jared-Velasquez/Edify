export enum NavbarActionTypes {
    ExpandNavbar = '[Navbar] expand',
    CollapseNavbar = '[Navbar] collapse',
    ToggleNavbar = '[Navbar] toggle',
    GetCourses = '[Navbar] courses',
    LoadCoursesSuccess = '[Navbar] Courses Loaded Success',
    LoadCoursesError = '[Navbar] Courses Loaded Error',
};

export enum CourseActionTypes {
    GetScores = '[Courses] scores',
    LoadScoresSuccess = '[Courses] Scores Loaded Success',
    LoadScoresError = '[Courses] Scores Loaded Error',
};

export enum UserActionTypes {
    GetUser = '[User] user',
    LoadUserSuccess = '[User] User Loaded Success',
    LoadUserError = '[User] User Loaded Error',
};