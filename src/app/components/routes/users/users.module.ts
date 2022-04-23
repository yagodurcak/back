import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';

import { AngularFireModule } from "@angular/fire";
import { AngularFireAuthModule } from "@angular/fire/auth";
import { ListUsersComponent } from './list-users/list-users.component';

const routes: Routes = [
  { path: 'list', component: ListUsersComponent }
];


var config = {
  apiKey: "AIzaSyCxxCt4g3AahWBndc6Ks6zSyITjUGAIK-0",
  authDomain: "indumet-sso.firebaseapp.com",
  databaseURL: "https://indumet-sso.firebaseio.com",
  projectId: "indumet-sso",
  storageBucket: "indumet-sso.appspot.com",
  messagingSenderId: "747899036952"
};
@NgModule({
  imports: [
    CommonModule,
    AngularFireModule.initializeApp(config),
    AngularFireAuthModule,
    RouterModule.forChild(routes),
  ],
  declarations: [LoginComponent, ListUsersComponent],
  exports: [
      RouterModule
  ]
})
export class UsersModule { }
