import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Provider } from '../interfaces/provider';

@Injectable({
  providedIn: 'root'
})
export class ProvidersService {


  constructor(private http:HttpClient) { }

  public findAll(pageSize:number, pageNumber:number, orderField:string, orderCriteria:string){
    let parameters:string  = "?pageSize=" + pageSize + "&pageNumber=" + pageNumber + "&orderField=" + orderField + "&orderCriteria=" + orderCriteria; 
    return this.http.get(environment.apiEndpoint + '/providers' + parameters);
  }

  public find(id:number){
    return this.http.get(environment.apiEndpoint + '/providers/' + id);
  }

  public save(provider:Provider){
    if(provider.id == null)
      return this.http.post(environment.apiEndpoint + '/providers', provider);
    else
    return this.http.put(environment.apiEndpoint + '/providers', provider);
  }

  public delete(id:number){
    return this.http.delete(environment.apiEndpoint + '/providers/' + id);
  }
}
