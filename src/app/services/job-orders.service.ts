import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { JobOrder } from '../interfaces/job-order';
import { State } from '../interfaces/state';


@Injectable({
  providedIn: 'root'
})
export class JobOrdersService {


  constructor(private http:HttpClient) { }

  public findAll(pageSize:number, pageNumber:number, orderField:string, orderCriteria:string, state:string, query:string){
    let parameters:string  = "?pageSize=" + pageSize + "&pageNumber=" + pageNumber + "&orderField=" + orderField + "&orderCriteria=" + orderCriteria + (state != "" ? "&states=" + state : "") + (query != "" ? "&query=" + query : ""); 
    return this.http.get(environment.apiEndpoint + '/jobOrders' + parameters);
  }
  
  public find(id:number){
    return this.http.get(environment.apiEndpoint + '/jobOrders/' + id);
  }

  public save(jobOrder:JobOrder){
    if(jobOrder.id == null)
      return this.http.post(environment.apiEndpoint + '/jobOrders', jobOrder);
    else
      return this.http.put(environment.apiEndpoint + '/jobOrders', jobOrder);
  }

  public delete(id:number){
    return this.http.delete(environment.apiEndpoint + '/jobOrders/' + id);
  }

  public changeState(id:number, state:State){
    return this.http.post(environment.apiEndpoint + '/jobOrders/' + id + '/states', state);
  }
}