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

export interface CalendarDay {
    dayTitle: string,
    dayNumber: number,
    monthNumber: number,
    yearNumber: number,
};