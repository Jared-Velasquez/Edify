export interface TokenResponse {
    token: string,
};

export interface CourseContent {
    courseContentId: number,
    url: string,
};

export interface Assignment {
    assignmentId: number,
    title: string,
    description: string,
    dueAt: string,
    unlockAt: string,
    lockAt: string,
    pointsPossible: number,
    createdAt: string,
    visible: boolean,
};

export interface Module {
    moduleId: number,
    title: string,
    assignments: Assignment[],
};

export interface Course {
    courseId: number,
    title: string,
    code: string,
    publiclyVisible: boolean,
    units: number,
    courseContent?: CourseContent,
    modules?: Module[],
    announcements?: Announcement[],
};

export interface CourseResponse {
    courses: Course[],
};

export interface Announcement {
    announcementId: number,
    title: string,
    description: string,
    createdAt: string,
};

export interface AnnouncementResponse {
    announcements: Announcement[],
};