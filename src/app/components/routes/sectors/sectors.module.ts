import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { ListSectorsComponent } from './list-sectors/list-sectors.component';
import { SaveSectorsComponent } from './save-sectors/save-sectors.component';
import { InlineEditorModule } from '@qontu/ngx-inline-editor';


const routes: Routes = [
    { path: 'list', component: ListSectorsComponent },
    { path: 'save/:id', component: SaveSectorsComponent }
];

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(routes),
        FormsModule,
        InlineEditorModule
    ],
    declarations: [
        ListSectorsComponent,
        SaveSectorsComponent
    ],
    exports: [
        RouterModule
    ]
})
export class SectorsModule { }