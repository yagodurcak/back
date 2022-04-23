import { LayoutComponent } from '../layout/layout.component';

import { LoginComponent } from './users/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { RecoverComponent } from './pages/recover/recover.component';
import { LockComponent } from './pages/lock/lock.component';
import { MaintenanceComponent } from './pages/maintenance/maintenance.component';
import { Error404Component } from './pages/error404/error404.component';
import { Error500Component } from './pages/error500/error500.component';
import { SecureInnerPagesGuard } from '../../guard/secure-inner-pages.guard';
import { AuthGuard } from '../../guard/auth.guard';

export const routes = [

    {
        path: '',
        component: LayoutComponent,
        children: [
            { path: 'dashboard', loadChildren: './dashboard/dashboard.module#DashboardModule' },
            { path: 'widgets', loadChildren: './widgets/widgets.module#WidgetsModule' },
            { path: 'material', loadChildren: './material/material.module#MaterialModule' },
            { path: 'elements', loadChildren: './elements/elements.module#ElementsModule' },
            { path: 'forms', loadChildren: './forms/forms.module#FormsModule' },
            { path: 'charts', loadChildren: './charts/charts.module#ChartsModule' },
            { path: 'tables', loadChildren: './tables/tables.module#TablesModule' },
            { path: 'maps', loadChildren: './maps/maps.module#MapsModule' },
            { path: 'blog', loadChildren: './blog/blog.module#BlogModule' },
            { path: 'ecommerce', loadChildren: './ecommerce/ecommerce.module#EcommerceModule' },
            { path: 'extras', loadChildren: './extras/extras.module#ExtrasModule' },
            { path: '', redirectTo: 'home', pathMatch: 'full'},
            {
                path: 'home',
                loadChildren: './home/home.module#HomeModule',
                canActivate:[AuthGuard],
                canActivateChild:[AuthGuard],
                data: { roles: ['admin', 'technique','engineering']}
            },
            {
                path: 'kinds',
                loadChildren: './kinds/kinds.module#KindsModule',
                canActivate:[AuthGuard],
                canActivateChild:[AuthGuard],
                data: { roles: ['admin']}
            },
            {
              path: 'purchaseConditions',
              loadChildren: './purchase-conditions/purchase-conditions.module#PurchaseConditionsModule',
              canActivate:[AuthGuard],
              canActivateChild:[AuthGuard],
              data: { roles: ['admin']}
            },
            {
                path: 'workers',
                loadChildren: './workers/workers.module#WorkersModule',
                canActivate:[AuthGuard],
                canActivateChild:[AuthGuard],
                data: { roles: ['admin']}
            },
            {
                path: 'providers',
                loadChildren: './providers/providers.module#ProvidersModule',
                canActivate:[AuthGuard],
                canActivateChild:[AuthGuard],
                data: { roles: ['admin']}
            },
            {
                path: 'sectors',
                loadChildren: './sectors/sectors.module#SectorsModule',
                canActivate:[AuthGuard],
                canActivateChild:[AuthGuard],
                data: { roles: ['admin']}
            },
            {
                path: 'users',
                loadChildren: './users/users.module#UsersModule',
                canActivate:[AuthGuard],
                canActivateChild:[AuthGuard],
                data: { roles: ['admin']}
            },
            {
                path: 'requisitions',
                loadChildren: './requisitions/requisitions.module#RequisitionsModule',
                canActivate:[AuthGuard],
                canActivateChild:[AuthGuard],
                data: { roles: ['admin', 'purchase','storeroom']}
            },
            {
                path: 'purchases',
                loadChildren: './purchases/purchases.module#PurchasesModule',
                canActivate:[AuthGuard],
                canActivateChild:[AuthGuard],
                data: { roles: ['admin', 'purchase']}
            },
            {
                path: 'jobs',
                loadChildren: './jobs/jobs.module#JobsModule',
                canActivateChild:[AuthGuard],
                data: { roles: ['admin', 'technique','engineering']}
            },
            {
                path: 'jobOrders',
                loadChildren: './job-orders/job-orders.module#JobOrdersModule',
                canActivateChild:[AuthGuard],
                data: { roles: ['admin', 'technique','engineering']}
             }
        ]
    },

    // Not lazy-loaded routes
    { path: 'login', component: LoginComponent, canActivate:[SecureInnerPagesGuard]},
    { path: 'register', component: RegisterComponent },
    { path: 'recover', component: RecoverComponent },
    { path: 'lock', component: LockComponent },
    { path: 'maintenance', component: MaintenanceComponent },
    { path: '404', component: Error404Component },
    { path: '500', component: Error500Component },

    // Not found
    { path: '**', redirectTo: 'home' }

];
