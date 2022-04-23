import { Component, OnInit, Input} from '@angular/core';
import { PurchaseOrder } from '../../../../interfaces/purchase-order';
import { PurchaseOrderItem } from '../../../../interfaces/purchase-order-item';

@Component({
  selector: 'app-print-purchase-orders',
  templateUrl: './print-purchase-orders.component.html',
  styleUrls: ['./print-purchase-orders.component.scss']
})
export class PrintPurchaseOrdersComponent implements OnInit {

  @Input() purchaseOrder:PurchaseOrder;

  constructor() { }

  ngOnInit() {
  }
  
  public purchaseOrderNumber(id:number){
    return ("00000" + id).slice(-5);
  }

  public subtotal(purchaseOrderItems:Array<PurchaseOrderItem>):number{
    let subtotal:number = 0;
    purchaseOrderItems.forEach((item:PurchaseOrderItem) => subtotal += item.price * item.amount);
    return subtotal;
  }

  public iva(purchaseOrderItems:Array<PurchaseOrderItem>):number{
    let iva:number = 0;
    purchaseOrderItems.forEach((item:PurchaseOrderItem) => iva += item.price * item.amount * ((item.aliquot ? item.aliquot : 21) / 100));
    return iva;
  }
}
