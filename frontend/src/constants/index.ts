interface Footer {
    title: string,
    links: {
        name: string,
        link: string
    }[]
};

interface Links {
    id: string,
    icon: string,
    link: string
};

export const footerLinks: Footer[] = [
    {
        title: "Contact",
        links: [
            {
                name: "Directory",
                link: "/"
            },
            {
                name: "Academic Calendar",
                link: "/"
            },
            {
                name: "Careers",
                link: "/"
            },
            {
                name: "Contact Us",
                link: "/"
            },
            {
                name: "Media and Journalists",
                link: "/"
            },
        ]
    },
    {
        title: "Community",
        links: [
            {
                name: "Alumni",
                link: "/"
            },
            {
                name: "Parents & Families",
                link: "/"
            },
            {
                name: "Faculty",
                link: "/"
            },
            {
                name: "Staff",
                link: "/"
            },
            {
                name: "Emerti & Retirees",
                link: "/"
            },
        ]
    },
    {
        title: "Partner",
        links: [
            {
                name: "Our Partners",
                link: "/"
            },
            {
                name: "Become a Partner",
                link: "/"
            },
        ]
    }
];

export const socialMedia: Links[] = [
    {
        id: "facebook",
        icon: "assets/img/icons8-facebook-48.png",
        link: "facebook.com"
    },
    {
        id: "instagram",
        icon: "assets/img/icons8-instagram-48.png",
        link: "instagram.com"
    },
    {
        id: "linkedin",
        icon: "assets/img/icons8-linkedin-48.png",
        link: "linkedin.com"
    },
    {
        id: "twitter",
        icon: "assets/img/icons8-twitter-48.png",
        link: "twitter.com"
    },
];

export const navLinks: Links[] = [
    {
        id: "account",
        icon: "",
        link: ""
    },
    {
        id: "dashboard",
        icon: "",
        link: ""
    },
    {
        id: "courses",
        icon: "",
        link: ""
    },
    {
        id: "calendar",
        icon: "",
        link: ""
    },
    {
        id: "inbox",
        icon: "",
        link: ""
    },
    {
        id: "history",
        icon: "",
        link: ""
    },
    {
        id: "search",
        icon: "",
        link: ""
    },
    {
        id: "help",
        icon: "",
        link: ""
    },
];