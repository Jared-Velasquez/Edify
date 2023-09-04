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

export const CourseEmpty: Course = {
    courseId: 0,
    title: "",
    code: "",
    publiclyVisible: false,
    units: 0,
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
    createdAt: string,
};

export interface AnnouncementResponse {
    announcements: Announcement[],
};