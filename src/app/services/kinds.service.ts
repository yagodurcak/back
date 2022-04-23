import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Kind } from '../interfaces/kind';

@Injectable({
  providedIn: 'root'
})
export class KindsService {


  constructor(private http:HttpClient) { }

  public findAll(pageSize:number, pageNumber:number, orderField:string, orderCriteria:string, context:string){
    let parameters:string  = "?pageSize=" + pageSize + "&pageNumber=" + pageNumber + "&orderField=" + orderField + "&orderCriteria=" + orderCriteria + "&context=" + context; 
    return this.http.get(environment.apiEndpoint + '/kinds' + parameters);
  }

  public find(id:number){
    return this.http.get(environment.apiEndpoint + '/kinds/' + id);
  }

  public save(kind:Kind){
    if(kind.id == null)
      return this.http.post(environment.apiEndpoint + '/kinds', kind);
    else
    return this.http.put(environment.apiEndpoint + '/kinds', kind);
  }

  public delete(id:number){
    return this.http.delete(environment.apiEndpoint + '/kinds/' + id);
  }
}
