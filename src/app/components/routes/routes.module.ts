import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TranslatorService } from '../core/translator/translator.service';
import { MenuService } from '../core/menu/menu.service';
import { SharedModule } from '../shared/shared.module';
import { PagesModule } from './pages/pages.module';
import { UsersModule } from './users/users.module'
import { menu } from './menu';
import { routes } from './routes';

import { AuthGuard } from "../../guard/auth.guard";
import { SecureInnerPagesGuard } from "../../guard/secure-inner-pages.guard";

@NgModule({
    imports: [
        SharedModule,
        RouterModule.forRoot(routes, {useHash:true}),
        PagesModule,
        UsersModule
    ],
    providers:[
        AuthGuard, 
        SecureInnerPagesGuard
    ],
    declarations: [],
    exports: [
        RouterModule
    ]
})

export class RoutesModule {
    constructor(public menuService: MenuService, tr: TranslatorService) {
        menuService.addMenu(menu);
    }
}
