import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, CanActivateChild } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';

const swal = require('sweetalert');

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(
    public authService: AuthenticationService,
    public router: Router
  ){ }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    localStorage.setItem("indumet-workload-last-url", state.url);
    if(!this.authService.isUserLoggedIn()) {
      swal('Error de autenticación!', 'Debes iniciar sesión para acceder al sistema.', 'error');
      this.router.navigate(['login']);
    } else {
      if(!this.authService.userHaveClaims()) {
        swal('Esperando autorización!', 'Un administrador del sistema le asignará los permisos correspondientes para poder ingresar al sistema.', 'info');
        this.router.navigate(['login']);
      } else {
        let claims:Array<string> = next.data.roles;
        let havePermissions = false;
        for(let i = 0;i < claims.length;i++)
          if(this.authService.userHaveClaim(claims[i]))
            havePermissions = true;
          
        if(!havePermissions){
          swal('Permiso denegado!', 'No tiene los permisos requeridos para acceder a este sector del sistema.', 'info');  
          this.router.navigate(this.authService.getHomeByClain());
        } else {
          return true;
        }
      }
    }
  }

  canActivateChild(next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
     return this.canActivate(next, state);
  }
}
