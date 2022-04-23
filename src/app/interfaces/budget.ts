import { Provider } from "./provider";
import { PurchaseOrder } from "./purchase-order";

export interface Budget {
    id: number;
    selected: boolean;
    sent: boolean;
    message: string;
    created: string;
    provider: Provider;
    purchase_order: PurchaseOrder;
}
