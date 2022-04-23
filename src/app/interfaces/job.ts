import { Kind } from "./kind";

export interface Job {
    id: number;
    number:string;
    description:string;
    item:string,
    kind:Kind,
    searchField:string,
    components:Array<Job>,
    pictures:Array<any>,
    planes:Array<any>,
    documents:Array<any>
}
