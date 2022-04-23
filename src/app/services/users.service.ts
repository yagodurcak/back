import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
const swal = require('sweetalert');

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(public http: HttpClient) {
    
  }

  public findAll(){
    return this.http.get(environment.apiEndpoint + '/users');
  }

  public find(uid:string){
    return this.http.get(environment.apiEndpoint + '/users/' + uid);
  }

  public setClaims(uid:string, claims:any){
    return this.http.post(environment.apiEndpoint + '/users/claims/' + uid, claims);
  }


}
