import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Job } from '../interfaces/job';
@Injectable({
  providedIn: 'root'
})
export class JobsService {


  constructor(private http:HttpClient) { }

  public findAll(pageSize:number, pageNumber:number, orderField:string, orderCriteria:string, query:string){
    let parameters:string  = "?pageSize=" + pageSize + "&pageNumber=" + pageNumber + "&orderField=" + orderField + "&orderCriteria=" + orderCriteria + (query != "" ? "&query=" + query : ""); 
    return this.http.get(environment.apiEndpoint + '/jobs' + parameters);
  }

  public search(query:string){
    let parameters:string  = "?query=" + query; 
    return this.http.get(environment.apiEndpoint + '/jobs/search' + parameters);
  }

  public find(id:number){
    return this.http.get(environment.apiEndpoint + '/jobs/' + id);
  }

  public findJobOrders(id:number, year?:number, month?:number){
    if(year){
      if(month){
        return this.http.get(environment.apiEndpoint + '/jobs/' + id + '/jobOrders?year=' + year + '&month=' + month);
      } else {
        return this.http.get(environment.apiEndpoint + '/jobs/' + id + '/jobOrders?year=' + year);
      }
    } else {
      return this.http.get(environment.apiEndpoint + '/jobs/' + id + '/jobOrders');
    }
    
  }

  public save(job:Job){
    if(job["id"] == null)
      return this.http.post(environment.apiEndpoint + '/jobs', job);
    else
      return this.http.put(environment.apiEndpoint + '/jobs', job);
  }

  public delete(id:number){
    return this.http.delete(environment.apiEndpoint + '/jobs/' + id);
  }

  public saveComponent(job:Job, parentId:number){
      return this.http.post(environment.apiEndpoint + '/jobs/' + parentId + '/components' , job);
  }

  public linkComponent(parentId:number, childId:number){
    return this.http.put(environment.apiEndpoint + '/jobs/' + parentId + '/components' , {id:null, job_id:parentId, sub_job_id:childId});
}
}
