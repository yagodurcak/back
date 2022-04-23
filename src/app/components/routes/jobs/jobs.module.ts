import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { AuthGuard } from '../../../guard/auth.guard';
import { ListJobsComponent } from './list-job/list-jobs.component';
import { SaveJobsComponent } from './save-job/save-jobs.component';
import { ViewJobsComponent } from './view-job/view-jobs.component';

const routes: Routes = [
    { path: 'list', component: ListJobsComponent, canActivate:[AuthGuard], canActivateChild:[AuthGuard], data: { roles: ['admin', 'engineering','technique']}},
    { path: 'save', component: SaveJobsComponent , canActivate:[AuthGuard], canActivateChild:[AuthGuard], data: { roles: ['admin']}},
    { path: 'view/:id', component: ViewJobsComponent , canActivate:[AuthGuard], canActivateChild:[AuthGuard], data: { roles: ['admin', 'engineering','technique']}}
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(routes),
        FormsModule
    ],
    declarations: [
        ListJobsComponent,
        SaveJobsComponent,
        ViewJobsComponent
    ],
    exports: [
        RouterModule
    ]
})
export class JobsModule { }