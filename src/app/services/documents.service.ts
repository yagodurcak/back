import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DocumentsService {

    constructor(private http:HttpClient) { }

    public save(source:string, document:any, sourceId:number){
        if(document.id == null)
            return this.http.post(environment.apiEndpoint + '/' + source + '/' + sourceId + '/documents', document);
        else
            return null;
    }

    public delete(source:string, id:number, sourceId:number){
        return this.http.delete(environment.apiEndpoint + '/' + source + '/' + sourceId + '/documents/' + id);
    }
}
