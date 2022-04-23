import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Ng2TableModule } from 'ng2-table/ng2-table';

import { SharedModule } from '../../shared/shared.module';
import { ListKindsComponent } from './list-kinds/list-kinds.component';
import { SaveKindsComponent } from './save-kinds/save-kinds.component';

const routes: Routes = [
    { path: 'list', component: ListKindsComponent },
    { path: 'save/:id', component: SaveKindsComponent }
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(routes),
        Ng2TableModule
    ],
    declarations: [
        ListKindsComponent,
        SaveKindsComponent
    ],
    exports: [
        RouterModule
    ]
})
export class KindsModule { }