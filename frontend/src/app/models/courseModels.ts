export interface ContentInterface {
    courseContentId: number,
    url: string,
};

export interface CourseInterface {
    courseId: number,
    title: string,
    units: number,
    courseContent: ContentInterface,
};