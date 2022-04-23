import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Purchase } from '../interfaces/purchase';
import { PurchaseState } from '../interfaces/purchase-state';
@Injectable({
  providedIn: 'root'
})
export class PurchasesService {
  constructor(private http:HttpClient) { }

  public findAll(pageSize:number, pageNumber:number, orderField:string, orderCriteria:string, state:string, query:string){
    let parameters:string  = "?pageSize=" + pageSize + "&pageNumber=" + pageNumber + "&orderField=" + orderField + "&orderCriteria=" + orderCriteria + (state != "" ? "&states=" + state : "") + (query != "" ? "&query=" + query : ""); 
    return this.http.get(environment.apiEndpoint + '/purchases' + parameters);
  }

  public find(id:number){
    return this.http.get(environment.apiEndpoint + '/purchases/' + id);
  }

  public save(purchase:Purchase){
    if(purchase["id"] == null)
      return this.http.post(environment.apiEndpoint + '/purchases', purchase);
    else
      return this.http.put(environment.apiEndpoint + '/purchases', purchase);
  }

  public delete(id:number){
    return this.http.delete(environment.apiEndpoint + '/purchases/' + id);
  }

  public linkToPurchase(parentId:number, childrenId:Array<number>){
    return this.http.put(environment.apiEndpoint + '/purchases/' + parentId + '/purchase' , childrenId);
  }

  public changeState(id:number, state:PurchaseState){
    return this.http.post(environment.apiEndpoint + '/purchases/' + id + '/states', state);
  }
}
