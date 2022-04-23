import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { HomeComponent } from './home/home.component';
import { PipesModule } from '../../../pipes/pipes.module';
import {PrintHomeComponent} from './print-home/print-home.component';


const routes: Routes = [
    { path: '', component: HomeComponent },
];

@NgModule({
    imports: [
        SharedModule,
        PipesModule,
        RouterModule.forChild(routes)
    ],
    declarations: [HomeComponent, PrintHomeComponent],
    exports: [
        RouterModule
    ]
})
export class HomeModule { }
