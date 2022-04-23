import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { PipesModule } from '../../../pipes/pipes.module';
import { ListRequisitionsComponent } from './list-requisitions/list-requisitions.component';
import { SaveRequisitionsComponent } from './save-requisitions/save-requisitions.component';
import { ViewRequisitionsComponent } from './view-requisitions/view-requisitions.component';

const routes: Routes = [
    { path: 'list', component: ListRequisitionsComponent },
    { path: 'save', component: SaveRequisitionsComponent },
    { path: 'view/:id', component: ViewRequisitionsComponent }
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(routes),
        FormsModule,
        PipesModule
    ],
    declarations: [
        ListRequisitionsComponent,
        SaveRequisitionsComponent,
        ViewRequisitionsComponent
    ],
    exports: [
        RouterModule
    ]
})
export class RequisitionsModule { }