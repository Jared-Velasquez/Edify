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

export interface AnnouncementUnitResponse {
    announcementId: number,
    title: string,
    description: string,
    createdAt: string,
};

export interface AnnouncementResponse {
    announcements: AnnouncementUnitResponse[],
};