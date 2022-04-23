import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { ToasterModule } from 'angular2-toaster/angular2-toaster';


import {
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatStepperModule,
} from '@angular/material';

import { AccordionModule } from 'ngx-bootstrap/accordion';
import { AlertModule } from 'ngx-bootstrap/alert';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';
import { RatingModule } from 'ngx-bootstrap/rating';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { DatepickerModule } from 'ngx-bootstrap/datepicker';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';

import { FlotDirective } from './directives/flot/flot.directive';
import { SparklineDirective } from './directives/sparkline/sparkline.directive';
import { EasypiechartDirective } from './directives/easypiechart/easypiechart.directive';
import { ColorsService } from './colors/colors.service';
import { CheckallDirective } from './directives/checkall/checkall.directive';
import { VectormapDirective } from './directives/vectormap/vectormap.directive';
import { NowDirective } from './directives/now/now.directive';
import { ScrollableDirective } from './directives/scrollable/scrollable.directive';
import { JqcloudDirective } from './directives/jqcloud/jqcloud.directive';
import { InlineEditorModule } from '@qontu/ngx-inline-editor';
import { FileUploadModule } from 'ng2-file-upload';
import { ImageCropperModule } from 'ng2-img-cropper';
import { OrderModule } from 'ngx-order-pipe';
import { DetailJobsComponent } from "./detail-job/detail-jobs.component";
import { AddJobsComponent } from './add-job/add-jobs.component';
import { CardJobComponent } from './card-job/card-job.component';
import { SelectJobComponent } from './select-job/select-job.component';
import { LinkJobComponent } from './link-job/link-job.component';
import { PicturesJobComponent } from './pictures-job/pictures-job.component';
import { PlanesJobComponent } from './planes-job/planes-job.component';
import { HeaderJobComponent } from './header-job/header-job.component';
import { AddJobOrderComponent } from './add-job-order/add-job-order.component';
import { HeaderJobOrderComponent } from './header-job-order/header-job-order.component';
import { StatesJobOrderComponent } from './states-job-order/states-job-order.component';
import { HistoryJobComponent } from './history-job/history-job.component';
import { WorkRecordBoxComponent } from './work-record-box/work-record-box.component';
import { DocumentsJobComponent } from './documents-job/documents-job.component';
import { DocumentsJobOrderComponent } from './documents-job-order/documents-job-order.component';
import { AddPurchasesComponent } from './add-purchases/add-purchases.component';
import { HeaderPurchaseComponent } from './header-purchase/header-purchase.component';
import { HeaderRequisitionComponent } from './header-requisition/header-requisition.component';
import { StatesPurchaseComponent } from './states-purchase/states-purchase.component';
import { DocumentsPurchaseComponent } from './documents-purchase/documents-purchase.component';
import { SavePurchaseOrdersComponent } from './purchase-orders/save-purchase-orders/save-purchase-orders.component';
import { PipesModule } from '../../pipes/pipes.module';
import { PrintPurchaseOrdersComponent } from './purchase-orders/print-purchase-orders/print-purchase-orders.component';
import { NgxSelectModule } from 'ngx-select-ex';
import { registerLocaleData } from '@angular/common';
import localeEs from '@angular/common/locales/es-AR';
registerLocaleData(localeEs, 'es-AR');


// https://angular.io/styleguide#!#04-10
@NgModule({
    imports: [
        NgxSelectModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateModule,
        AccordionModule.forRoot(),
        AlertModule.forRoot(),
        ButtonsModule.forRoot(),
        CarouselModule.forRoot(),
        CollapseModule.forRoot(),
        DatepickerModule.forRoot(),
        BsDatepickerModule.forRoot(),
        BsDropdownModule.forRoot(),
        ModalModule.forRoot(),
        PaginationModule.forRoot(),
        ProgressbarModule.forRoot(),
        RatingModule.forRoot(),
        TabsModule.forRoot(),
        TimepickerModule.forRoot(),
        TooltipModule.forRoot(),
        PopoverModule.forRoot(),
        TypeaheadModule.forRoot(),
        ToasterModule,
        // Material Modules
        MatAutocompleteModule,
        MatButtonModule,
        MatButtonToggleModule,
        MatCardModule,
        MatCheckboxModule,
        MatChipsModule,
        MatTableModule,
        MatDatepickerModule,
        MatDialogModule,
        MatExpansionModule,
        MatFormFieldModule,
        MatGridListModule,
        MatIconModule,
        MatInputModule,
        MatListModule,
        MatMenuModule,
        MatPaginatorModule,
        MatProgressBarModule,
        MatProgressSpinnerModule,
        MatRadioModule,
        MatRippleModule,
        MatSelectModule,
        MatSidenavModule,
        MatSlideToggleModule,
        MatSliderModule,
        MatSnackBarModule,
        MatSortModule,
        MatTabsModule,
        MatToolbarModule,
        MatTooltipModule,
        MatNativeDateModule,
        MatStepperModule,
        InlineEditorModule,
        FileUploadModule,
        ImageCropperModule,
        OrderModule,
        PipesModule,
        RouterModule
    ],
    providers: [
        ColorsService
    ],
    declarations: [
        FlotDirective,
        SparklineDirective,
        EasypiechartDirective,
        CheckallDirective,
        VectormapDirective,
        NowDirective,
        ScrollableDirective,
        JqcloudDirective,
        DetailJobsComponent,
        AddJobsComponent,
        CardJobComponent,
        SelectJobComponent,
        LinkJobComponent,
        PicturesJobComponent,
        PlanesJobComponent,
        HeaderJobComponent,
        AddJobOrderComponent,
        HeaderJobOrderComponent,
        StatesJobOrderComponent,
        HistoryJobComponent,
        WorkRecordBoxComponent,
        DocumentsJobComponent,
        DocumentsJobOrderComponent,
        AddPurchasesComponent,
        HeaderPurchaseComponent,
        StatesPurchaseComponent,
        DocumentsPurchaseComponent,
        SavePurchaseOrdersComponent,
        PrintPurchaseOrdersComponent,
        HeaderRequisitionComponent
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateModule,
        RouterModule,
        AccordionModule,
        AlertModule,
        ButtonsModule,
        CarouselModule,
        CollapseModule,
        DatepickerModule,
        BsDatepickerModule,
        BsDropdownModule,
        ModalModule,
        PaginationModule,
        ProgressbarModule,
        RatingModule,
        TabsModule,
        TimepickerModule,
        TooltipModule,
        PopoverModule,
        TypeaheadModule,
        ToasterModule,
        FlotDirective,
        SparklineDirective,
        EasypiechartDirective,
        CheckallDirective,
        VectormapDirective,
        NowDirective,
        ScrollableDirective,
        JqcloudDirective,
        // Material Modules
        MatAutocompleteModule,
        MatButtonModule,
        MatButtonToggleModule,
        MatCardModule,
        MatCheckboxModule,
        MatChipsModule,
        MatTableModule,
        MatDatepickerModule,
        MatDialogModule,
        MatExpansionModule,
        MatFormFieldModule,
        MatGridListModule,
        MatIconModule,
        MatInputModule,
        MatListModule,
        MatMenuModule,
        MatPaginatorModule,
        MatProgressBarModule,
        MatProgressSpinnerModule,
        MatRadioModule,
        MatRippleModule,
        MatSelectModule,
        MatSidenavModule,
        MatSlideToggleModule,
        MatSliderModule,
        MatSnackBarModule,
        MatSortModule,
        MatTabsModule,
        MatToolbarModule,
        MatTooltipModule,
        MatNativeDateModule,
        MatStepperModule,
        DetailJobsComponent,
        AddJobsComponent,
        AddJobOrderComponent,
        HeaderJobOrderComponent,
        StatesJobOrderComponent,
        WorkRecordBoxComponent,
        DocumentsJobOrderComponent,
        AddPurchasesComponent,
        HeaderPurchaseComponent,
        StatesPurchaseComponent,
        DocumentsPurchaseComponent,
        SavePurchaseOrdersComponent,
        PrintPurchaseOrdersComponent,
        HeaderRequisitionComponent
    ]
})

// https://github.com/ocombe/ng2-translate/issues/209
export class SharedModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: SharedModule
        };
    }
}
