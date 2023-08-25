import { NavLinksInterface } from "./interfaceConstants";

export const navLinkOptions: NavLinksInterface[] = [
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
        subitems: [
            {
                icon: "fal fa-pencil",
                link: "courses/1",
                name: "Course 1"
            },
            {
                icon: "fal fa-pencil",
                link: "courses/2",
                name: "Course 2"
            },
            {
                icon: "fal fa-pencil",
                link: "courses/3",
                name: "Course 3"
            }
        ]
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