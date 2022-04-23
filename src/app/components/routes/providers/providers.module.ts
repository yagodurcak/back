import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { ListProvidersComponent } from './list-providers/list-providers.component';
import { SaveProvidersComponent } from './save-providers/save-providers.component';
import { InlineEditorModule } from '@qontu/ngx-inline-editor';


const routes: Routes = [
    { path: 'list', component: ListProvidersComponent },
    { path: 'save/:id', component: SaveProvidersComponent }
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(routes),
        FormsModule,
        InlineEditorModule
    ],
    declarations: [
        ListProvidersComponent,
        SaveProvidersComponent
    ],
    exports: [
        RouterModule
    ]
})
export class ProvidersModule { }