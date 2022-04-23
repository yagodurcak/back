import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PicturesService {

  constructor(private http:HttpClient) { }

  public save(picture:any, jobId:number){
      if(picture.id == null)
          return this.http.post(environment.apiEndpoint + '/jobs/' + jobId + '/pictures', picture.source);
      else
          return null;
  }

  public main(pictureId:number, jobId:number){
      return this.http.post(environment.apiEndpoint + '/jobs/' + jobId + '/pictures/' + pictureId + '/main', {});
  }

  public delete(id:number, jobId:number){
      return this.http.delete(environment.apiEndpoint + '/jobs/' + jobId + '/pictures/' + id);
  }
}
