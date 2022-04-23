import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './../../../../services/authentication.service'


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent  {

  constructor(private authService: AuthenticationService) {}

  // Open Popup to Login with Google Account
  googleLogin() {
    this.authService.loginWithGoogle()
      .then(res => {
        
      }, err => {

      });
  }
}
