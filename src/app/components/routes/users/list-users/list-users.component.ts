import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { UsersService } from '../../../../services/users.service';
import { UserData } from '../../../../interfaces/user-data';
const swal = require('sweetalert');

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html'
})
export class ListUsersComponent implements OnInit {

  public users:Array<UserData>=[];
  
  constructor(private _router:Router, private userService:UsersService) { 
      this.userService.findAll().subscribe((response:Array<UserData>) => {
        this.users = response;
    });
  }

  ngOnInit() {
   
  }

  updateClaims(uid:string, custom_claims:any){
    this.userService.setClaims(uid, custom_claims).subscribe(
      () => swal('Operación exitosa!', 'La operación se realizó con exito.', 'success')
    );
  }

}
