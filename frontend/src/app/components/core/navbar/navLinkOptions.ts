import { NavLinksInterface } from "src/constants/interfaceConstants";

export const navLinkOptions = (courses: NavLinksInterface[]): NavLinksInterface[] => {
    const options: NavLinksInterface[] = [
        {
            icon: "fal fa-user",
            link: "account",
            name: "Account",
        },
        {
            icon: "fal fa-tachometer",
            link: "dashboard",
            name: "Dashboard",
        },
        {
            icon: "fal fa-book",
            name: "Courses",
            subitems: courses,
        },
        {
            icon: "fal fa-calendar",
            link: "calendar",
            name: "Calendar",
        },
        {
            icon: "fal fa-cogs",
            link: "settings",
            name: "Settings",
        },
    ];

    return options;
}