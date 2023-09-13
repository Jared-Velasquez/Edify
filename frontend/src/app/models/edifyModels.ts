export interface DropDownMenuInterface {
    title: string,
    options: string[],
};

export const DropDownMenuEmpty: DropDownMenuInterface = {
    title: "",
    options: [],
};

export enum Role {
    STUDENT = "STUDENT",
    TEACHER = "TEACHER",
};

export enum Position {
   PROFESSOR = "PROFESSOR",
   TEACHING_PROFESSOR = "TEACHING_PROFESSOR",
   LECTURER = "LECTURER",
   ADJUNCT_PROFESSOR = "ADJUNCT_PROFESSOR",
   ASSISTANT_PROFESSOR = "ASSISTANT_PROFESSOR",
};

export interface CalendarDay {
    dayTitle: string,
    dayNumber: number,
    monthNumber: number,
    yearNumber: number,
};