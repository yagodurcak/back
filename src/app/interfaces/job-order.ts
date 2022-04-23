import { Job } from "./job";
import { Kind } from "./kind";
import { State } from "./state";

export interface JobOrder {
    id: number,
    purchase_order_number: string,
    in_date: string,
    compromised_date: string,
    delivery_date: string,
    jobs_amount: number,
    budgeted_hours: number,
    percentage_advance: number,
    description: string,
    bill_number: string,
    remit_number: string,
    real_hours_production: number,
    observations: string,
    current_state: string,
    job: Job,
    kind: Kind,
    states:Array<State>,
    documents:Array<any>
}
