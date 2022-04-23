import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { PurchaseOrder } from '../interfaces/purchase-order';
@Injectable({
  providedIn: 'root'
})
export class PurchaseOrdersService {
  constructor(private http:HttpClient) { }

  public find(id:number){
    return this.http.get(environment.apiEndpoint + '/purchaseOrders/' + id);
  }

  public save(purchaseOrder:PurchaseOrder){
    return this.http.post(environment.apiEndpoint + '/purchaseOrders', purchaseOrder);
  }
}
