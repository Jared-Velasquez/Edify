import { Course } from "src/app/models";
import { NavLinksInterface } from "src/constants/interfaceConstants";

export const navLinkOptions = (courses: Course[]): NavLinksInterface[] => {
    let coursesOptions: NavLinksInterface[] = [];

    courses.forEach((course) => {
        coursesOptions.push({
            name: course.title,
            link: `course/${course.courseId}`,
            icon: 'fal fa-pencil',
            subitems: [
            {
                link: `courses/${course.courseId}`,
                name: "Home",
            },
            {
                link: `courses/${course.courseId}/announcements`,
                name: "Announcements",
            },
            {
                link: `courses/${course.courseId}/modules`,
                name: "Modules",
            },
            {
                link: `courses/${course.courseId}/assignments`,
                name: "Assignments",
            },
            {
                link: `courses/${course.courseId}/people`,
                name: "People"
            }
            ]
        })
    })

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
            subitems: coursesOptions,
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