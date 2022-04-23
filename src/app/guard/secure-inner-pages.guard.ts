import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';

const swal = require('sweetalert');

@Injectable({
  providedIn: 'root'
})
export class SecureInnerPagesGuard implements CanActivate {

  constructor(
    public authService: AuthenticationService,
    public router: Router
  ){ }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
      if(state.url == '/login')
        return true;
      if(this.authService.isUserLoggedIn()) {
        if(this.authService.userHaveClaims()){
          swal("Ya iniciaste sesión!", "", "success");
          this.router.navigate(['home']);
        } else {
          swal('Esperando autorización!', 'Un administrador del sistema le asignará los permisos correspondientes para poder ingresar al sistema.', 'info');
        }
      }
      return true;
  }
}
