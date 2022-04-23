import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { PurchaseCondition } from '../interfaces/purchase-condition';

@Injectable({
  providedIn: 'root'
})
export class PurchaseConditionsService {


  constructor(private http:HttpClient) { }

  public findAll(pageSize:number, pageNumber:number, orderField:string, orderCriteria:string){
    let parameters:string  = "?pageSize=" + pageSize + "&pageNumber=" + pageNumber + "&orderField=" + orderField + "&orderCriteria=" + orderCriteria;
    return this.http.get(environment.apiEndpoint + '/purchaseConditions' + parameters);
  }

  public find(id:number){
    return this.http.get(environment.apiEndpoint + '/purchaseConditions/' + id);
  }

  public save(purchaseCondition: PurchaseCondition){
    if(purchaseCondition.id == null)
      return this.http.post(environment.apiEndpoint + '/purchaseConditions', purchaseCondition);
    else
    return this.http.put(environment.apiEndpoint + '/purchaseConditions', purchaseCondition);
  }

  public delete(id: number){
    return this.http.delete(environment.apiEndpoint + '/purchaseConditions/' + id);
  }
}
