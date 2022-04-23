import { Injectable } from '@angular/core';
import { Router } from "@angular/router";
import { auth } from 'firebase/app';
import { AngularFireAuth } from "@angular/fire/auth";
import { UsersService } from './users.service';


const swal = require('sweetalert');

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    public angularFireAuth: AngularFireAuth,
    public router: Router,
    public usersService:UsersService
  ) {
    this.angularFireAuth.authState.subscribe(userResponse => {
      if (userResponse) {
        localStorage.setItem('indumet-workload-user', JSON.stringify(userResponse));
      } else {
        localStorage.setItem('indumet-workload-user', null);
      }
    })
  }

  isUserLoggedIn() {
    return JSON.parse(localStorage.getItem('indumet-workload-user'));
  }

  userHaveClaims() {
    return JSON.parse(localStorage.getItem('indumet-workload-user-claims'));
  }

  userHaveClaim(claim:string) {
    return JSON.parse(localStorage.getItem('indumet-workload-user-claims'))[claim] === true;
  }

  getHomeByClain() {
    let claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));

    if(claims["admin"])
        return ['home'];

    if(claims["engineering"] || claims["technique"])
        return ['home'];

    if(claims["purchase"])
        return ['purchases', 'list'];

    if(claims["storeroom"])
        return ['requisitions', 'list'];

  }

  async  loginWithGoogle() {
    return this.angularFireAuth.auth.signInWithPopup(new auth.GoogleAuthProvider())
      .then(
        (userResponse:any) => {
          this.usersService.find(userResponse.user.uid).subscribe(
            (user:any) => {
              if(user.custom_claims && JSON.stringify(user.custom_claims) != '{}')
                localStorage.setItem('indumet-workload-user-claims', JSON.stringify(user.custom_claims));
              else 
                localStorage.setItem('indumet-workload-user-claims', null);

              if(!this.userHaveClaims()) {
                swal('Esperando autorización!', 'Un administrador del sistema le asignará los permisos correspondientes para poder ingresar al sistema.', 'info');
                this.router.navigate(['login']);
              } else {
                if(localStorage.getItem("indumet-workload-last-url") != null){
                  this.router.navigate(localStorage.getItem("indumet-workload-last-url").split("/"));
                } else {
                  
                  this.router.navigate(['home']);
                } 
              }
            }
          );
        }
      )
      .catch(
        (error:any) => {
        swal('Error de autenticación!', 'Ocurrió un error al iniciar sesión.', 'error');
        console.log(error);
      }
    );
  }

  async  logout() {
    return await this.angularFireAuth.auth.signOut().then(
      () => {
        localStorage.removeItem('indumet-workload-user')
        localStorage.removeItem('indumet-workload-user-claims')
        this.router.navigate(['login'])
      }
    );
  }

}
