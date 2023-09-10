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
}