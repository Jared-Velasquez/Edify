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
                link: "course/1",
                name: "Course 1"
            },
            {
                icon: "fal fa-pencil",
                link: "course/2",
                name: "Course 2"
            },
            {
                icon: "fal fa-pencil",
                link: "course/3",
                name: "Course 3",
                subitems: [
                    {
                        link: "course/3/home",
                        name: "Home"
                    },
                    {
                        link: "course/3/modules",
                        name: "Modules"
                    },
                    {
                        link: "course/3/assignments",
                        name: "Assignments"
                    }
                ]
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