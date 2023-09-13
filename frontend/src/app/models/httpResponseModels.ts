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
    dueAt: Date,
    unlockAt: Date,
    lockAt: Date,
    pointsPossible: number,
    createdAt: Date,
    visible: boolean,
    courseId?: number,
};

export const AssignmentEmpty: Assignment = {
    assignmentId: 0,
    title: "",
    description: "",
    dueAt: new Date(),
    unlockAt: new Date(),
    lockAt: new Date(),
    pointsPossible: 0,
    createdAt: new Date(),
    visible: false,
};

export interface AssignmentResponse {
    assignment: Assignment,
};

export interface AssignmentMultipleResponse {
    assignments: Assignment[],
};

export interface AssignmentCourseResponse {
    assignment: Assignment,
    courseId: number,
};

export interface AssignmentCourseMultipleResponse {
    assignments: AssignmentCourseResponse[],
};

export interface Score {
    assignmentId: number,
    score: number | null;
};

export interface ScoreResponse {
    assignments: Score[],
};

export interface Module {
    moduleId: number,
    title: string,
    assignments: Assignment[],
};

export const ModuleEmpty = {
    moduleId: 0,
    title: "",
    assignments: [],
};

export interface ModuleResponse {
    modules: Module[],
};

export interface Course {
    courseId: number,
    title: string,
    code: string,
    syllabusBody?: string,
    publiclyVisible: boolean,
    units: number,
    courseContent?: CourseContent,
    modules?: Module[],
    announcements?: Announcement[],
    firstName: string,
    lastName: string,
    position: string,
    department: string,
};

export const CourseEmpty: Course = {
    courseId: 0,
    title: "",
    code: "",
    publiclyVisible: false,
    units: 0,
    firstName: "",
    lastName: "",
    position: "",
    department: ""
  };

export interface CourseResponse {
    courses: Course[],
};

export interface Teacher {
    id: number,
    firstName: string,
    lastName: string,
    emailAddress: string,
    dob: string,
    role: string,
    gender: string,
    address: string,
    phoneNumber: string,
    department: string,
    position: string,
    enabled: boolean,
    authorities: {
        authority: string,
    }[],
    username: string,
    accountNonExpired: boolean,
    accountNonLocked: boolean,
    credentialsNonExpired: boolean,
}

export const TeacherEmpty: Teacher = {
    id: 0,
    firstName: "",
    lastName: "",
    emailAddress: "",
    dob: "",
    role: "",
    gender: "",
    address: "",
    phoneNumber: "",
    department: "",
    position: "",
    enabled: false,
    authorities: [],
    username: "",
    accountNonExpired: false,
    accountNonLocked: false,
    credentialsNonExpired: false,
}

export interface Announcement {
    announcementId: number,
    title: string,
    description: string,
    createdAt: Date,
};

export interface AnnouncementResponse {
    announcements: Announcement[],
};

export interface User {
    id: number,
    firstName: string,
    lastName: string,
    emailAddress: string,
    dob: string,
    role: string,
    gender: string,
    address: string,
    phoneNumber: string,
    enabled: boolean,
    authorities: {
        authority: string,
    }[],
    username: string,
    accountNonExpired: boolean,
    accountNonLocked: boolean,
    credentialsNonExpired: boolean,
}

export interface UserResponse {
    user: User,
};

export const UserEmpty: User = {
    id: 0,
    firstName: "",
    lastName: "",
    emailAddress: "",
    dob: "",
    role: "",
    gender: "",
    address: "",
    phoneNumber: "",
    enabled: false,
    authorities: [],
    username: "",
    accountNonExpired: false,
    accountNonLocked: false,
    credentialsNonExpired: false,
};