import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Worker } from '../interfaces/worker';

@Injectable({
  providedIn: 'root'
})
export class WorkersService {


  constructor(private http:HttpClient) { }

  public findAll(pageSize:number, pageNumber:number, orderField:string, orderCriteria:string){
    let parameters:string  = "?pageSize=" + pageSize + "&pageNumber=" + pageNumber + "&orderField=" + orderField + "&orderCriteria=" + orderCriteria; 
    return this.http.get(environment.apiEndpoint + '/workers' + parameters);
  }

  public find(id:number){
    return this.http.get(environment.apiEndpoint + '/workers/' + id);
  }

  public save(worker:Worker){
    if(worker.id == null)
      return this.http.post(environment.apiEndpoint + '/workers', worker);
    else
    return this.http.put(environment.apiEndpoint + '/workers', worker);
  }

  public delete(id:number){
    return this.http.delete(environment.apiEndpoint + '/workers/' + id);
  }
}
