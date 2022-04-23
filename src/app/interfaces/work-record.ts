import { Worker } from "./worker";
import { JobOrder } from "./job-order";

export interface WorkRecord {
    id:number,
    hours:number,
    worker:Worker,
    jobOrder:JobOrder,
    registerDate:string,
    created:string,
}