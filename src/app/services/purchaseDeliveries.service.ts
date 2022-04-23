import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class PurchaseDeliveriesService {

    constructor(private http:HttpClient) { }

    public save(delivery:any, purchaseId:number){
        if(delivery.id == null)
            return this.http.post(environment.apiEndpoint + '/purchases/' + purchaseId + '/deliveries', delivery);
        else
            return null;
    }

    public delete(id:number, purchaseId:number){
        return this.http.delete(environment.apiEndpoint + '/purchases/' + purchaseId + '/deliveries/' + id);
    }
}
