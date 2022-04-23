import { UserData } from "./user-data";
import { Sector } from "./sector";
import { PurchaseState } from "./purchase-state";
import { Requisition } from "./requisition";
import { Budget } from "./budget";

export interface Purchase {
    id: number;
    buyer: string;
    bill_number: string;
    current_state: string;
    with_purchase_order: boolean;
    purchase_orders_id: string;
    providers_name: string;
    created: string;
    user: UserData;
    sector: Sector;
    states: Array<PurchaseState>;
    requisitions: Array<Requisition>;
    deliveries: Array<any>;
    budgets: Array<Budget>;
    documents: Array<any>;
}
