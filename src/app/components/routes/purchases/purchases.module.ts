import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { ListPurchasesComponent } from './list-purchases/list-purchases.component';
import { SavePurchasesComponent } from './save-purchases/save-purchases.component';
import { ViewPurchasesComponent } from './view-purchases/view-purchases.component';
import { FileUploadModule } from 'ng2-file-upload';

const routes: Routes = [
    { path: 'list', component: ListPurchasesComponent },
    { path: 'save', component: SavePurchasesComponent },
    { path: 'view/:id', component: ViewPurchasesComponent }
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(routes),
        FormsModule,
        FileUploadModule
    ],
    declarations: [
        ListPurchasesComponent,
        SavePurchasesComponent,
        ViewPurchasesComponent
    ],
    exports: [
        RouterModule
    ]
})
export class PurchasesModule { }
