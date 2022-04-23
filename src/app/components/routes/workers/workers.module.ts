import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Ng2TableModule } from 'ng2-table/ng2-table';

import { SharedModule } from '../../shared/shared.module';
import { ListWorkersComponent } from './list-workers/list-workers.component';
import { SaveWorkersComponent } from './save-workers/save-workers.component';

const routes: Routes = [
    { path: 'list', component: ListWorkersComponent },
    { path: 'save/:id', component: SaveWorkersComponent }
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(routes),
        Ng2TableModule
    ],
    declarations: [
        ListWorkersComponent,
        SaveWorkersComponent
    ],
    exports: [
        RouterModule
    ]
})
export class WorkersModule { }