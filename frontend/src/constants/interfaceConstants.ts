export interface Footer {
    title: string,
    links: {
        name: string,
        link: string,
    }[]
};

export interface Links {
    id: string,
    icon: string,
    link: string,
};

export interface NavLinks {
    icon: string,
    link?: string,
    name: string,
    items?: NavLinks[],
};