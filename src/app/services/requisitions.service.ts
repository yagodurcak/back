import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Requisition } from '../interfaces/requisition';
@Injectable({
  providedIn: 'root'
})
export class RequisitionsService {


  constructor(private http:HttpClient) { }

  public findAll(pageSize:number, pageNumber:number, orderField:string, orderCriteria:string, state:string){
    let parameters:string  = "?pageSize=" + pageSize + "&pageNumber=" + pageNumber + "&orderField=" + orderField + "&orderCriteria=" + orderCriteria + (state ? "&state=" + state : ""); 
    return this.http.get(environment.apiEndpoint + '/requisitions' + parameters);
  }

  public find(id:number){
    return this.http.get(environment.apiEndpoint + '/requisitions/' + id);
  }

  public save(requisition:Requisition){
    if(requisition["id"] == null)
      return this.http.post(environment.apiEndpoint + '/requisitions', requisition);
    else
      return this.http.put(environment.apiEndpoint + '/requisitions', requisition);
  }

  public delete(id:number){
    return this.http.delete(environment.apiEndpoint + '/requisitions/' + id);
  }

  public linkToPurchase(parentId:number, childrenId:Array<number>){
    return this.http.put(environment.apiEndpoint + '/requisitions/' + parentId + '/purchase' , childrenId);
}
}
