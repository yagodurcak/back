import { UserData } from "./user-data";
import { Sector } from "./sector";

export interface Requisition {
    id: number;
    description: string;
    amount: number;
    reason: string;
    requesting_date: string;
    unit_of_measurement: string;
    priority: number;
    user: UserData;
    created: string;
    requesting_sector: Sector;
    destination_sector: Sector;
    addToPurchase:boolean;
    purchaseId: number;
}