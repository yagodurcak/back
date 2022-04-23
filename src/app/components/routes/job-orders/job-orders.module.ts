import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { ListJobOrdersComponent } from './list-job-orders/list-job-orders.component';
import { SaveJobOrdersComponent } from './save-job-orders/save-job-orders.component';
import { ViewJobOrdersComponent } from './view-job-orders/view-job-orders.component';
import { InlineEditorModule } from '@qontu/ngx-inline-editor';
import { AuthGuard } from '../../../guard/auth.guard';


const routes: Routes = [
    { path: 'list', component: ListJobOrdersComponent, canActivate:[AuthGuard], canActivateChild:[AuthGuard], data: { roles: ['admin', 'engineering','technique'] }},
    { path: 'save', component: SaveJobOrdersComponent , canActivate:[AuthGuard], canActivateChild:[AuthGuard], data: { roles: ['admin']}},
    { path: 'view/:id', component: ViewJobOrdersComponent, canActivate:[AuthGuard], canActivateChild:[AuthGuard], data: { roles: ['admin', 'engineering','technique'] }}
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(routes),
        FormsModule,
        InlineEditorModule
    ],
    declarations: [
        ListJobOrdersComponent,
        SaveJobOrdersComponent,
        ViewJobOrdersComponent
    ],
    exports: [
        RouterModule
    ]
})
export class JobOrdersModule { }