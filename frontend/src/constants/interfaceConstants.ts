export interface FooterInterface {
    title: string,
    links: {
        name: string,
        link: string,
    }[]
};

export interface LinksInterface {
    id: string,
    icon: string,
    link: string,
};

export interface NavLinksInterface {
    icon: string,
    link?: string,
    name: string,
    items?: NavLinksInterface[],
};