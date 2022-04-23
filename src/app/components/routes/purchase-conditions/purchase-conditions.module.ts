import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Ng2TableModule } from 'ng2-table/ng2-table';

import { SharedModule } from '../../shared/shared.module';
import { ListPurchaseConditionsComponent } from './list-purchase-conditions/list-purchase-conditions.component';
import { SavePurchaseConditionsComponent } from './save-purchase-conditions/save-purchase-conditions.component';

const routes: Routes = [
    { path: 'list', component: ListPurchaseConditionsComponent },
    { path: 'save/:id', component: SavePurchaseConditionsComponent }
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(routes),
        Ng2TableModule
    ],
    declarations: [
        ListPurchaseConditionsComponent,
        SavePurchaseConditionsComponent
    ],
    exports: [
        RouterModule
    ]
})
export class PurchaseConditionsModule { }
