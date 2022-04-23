import { Provider } from "./provider";
import { PurchaseOrderItem } from "./purchase-order-item";
import {PurchaseCondition} from './purchase-condition';

export interface PurchaseOrder {
    id: number;
    description: string;
    delivery_address: string;
    emission_date: string;
    currency: string;
    other_tax: number;
    estimated_delivery_date: string;
    purchase_condition: PurchaseCondition;
    fiscal_condition: string;
    transport: string;
    created: string;
    provider: Provider;
    items: Array<PurchaseOrderItem>;
}
