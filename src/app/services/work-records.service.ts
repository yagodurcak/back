import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { WorkRecord } from '../interfaces/work-record';

@Injectable({
  providedIn: 'root'
})
export class WorkRecordsService {


  constructor(private http:HttpClient) { }

  public findAll(pageSize:number, pageNumber:number, orderField:string, orderCriteria:string, parent:string, foreignId:number){
    let parameters:string  = "?pageSize=" + pageSize + "&pageNumber=" + pageNumber + "&orderField=" + orderField + "&orderCriteria=" + orderCriteria + "&parent=" + parent + "&foreignId=" + foreignId; 
    return this.http.get(environment.apiEndpoint + '/workRecords' + parameters);
  }

  public find(id:number){
    return this.http.get(environment.apiEndpoint + '/workRecords/' + id);
  }

  public save(workRecord:WorkRecord){
    if(workRecord.id == null)
      return this.http.post(environment.apiEndpoint + '/workRecords', workRecord);
    else
    return this.http.put(environment.apiEndpoint + '/workRecords', workRecord);
  }

  public delete(id:number){
    return this.http.delete(environment.apiEndpoint + '/workRecords/' + id);
  }
}
