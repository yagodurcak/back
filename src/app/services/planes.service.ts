import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class PlanesService {

    constructor(private http:HttpClient) { }

    public save(plane:any, jobId:number){
        if(plane.id == null)
            return this.http.post(environment.apiEndpoint + '/jobs/' + jobId + '/planes', plane);
        else
            return null;
    }

    public delete(id:number, jobId:number){
        return this.http.delete(environment.apiEndpoint + '/jobs/' + jobId + '/planes/' + id);
    }
}
