import {Links} from "./Links";

export class Page {
    size: number = 10;
    index: number = 0;
    indices: number[] = [];
    content: any[] = [];
    filters: any[] = [];
    sorts: any[] = [];
    projections: string[] = [];
    links: Links;
}
