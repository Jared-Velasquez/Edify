export interface TokenResponse {
    token: string,
};

export interface CourseBasicUnitResponse {
    courseId: number,
    title: string,
};

export interface CourseBasicResponse {
    courses: CourseBasicUnitResponse[],
};