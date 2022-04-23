import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Sector } from '../interfaces/sector';

@Injectable({
  providedIn: 'root'
})
export class SectorsService {


  constructor(private http:HttpClient) { }

  public findAll(pageSize:number, pageNumber:number, orderField:string, orderCriteria:string){
    let parameters:string  = "?pageSize=" + pageSize + "&pageNumber=" + pageNumber + "&orderField=" + orderField + "&orderCriteria=" + orderCriteria; 
    return this.http.get(environment.apiEndpoint + '/sectors' + parameters);
  }

  public find(id:number){
    return this.http.get(environment.apiEndpoint + '/sectors/' + id);
  }

  public save(sector:Sector){
    if(sector.id == null)
      return this.http.post(environment.apiEndpoint + '/sectors', sector);
    else
    return this.http.put(environment.apiEndpoint + '/sectors', sector);
  }

  public delete(id:number){
    return this.http.delete(environment.apiEndpoint + '/sectors/' + id);
  }
}
