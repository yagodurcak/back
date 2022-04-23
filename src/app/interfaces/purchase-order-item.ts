export interface PurchaseOrderItem {
    id: number;
    item_number: number;
    description: string;
    amount: number;
    unit_of_measurement: number;
    aliquot: number;
    price: number;
    created: string;
}
