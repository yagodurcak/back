import { Component, OnInit, ViewEncapsulation, EventEmitter, ElementRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router} from "@angular/router";
import { PurchasesService } from '../../../../services/purchases.service';
import { Purchase } from '../../../../interfaces/purchase';
import { PurchaseState } from '../../../../interfaces/purchase-state';
import { RequisitionsService } from '../../../../services/requisitions.service';
import { PurchaseDeliveriesService } from '../../../../services/purchaseDeliveries.service';
import { KindsService } from '../../../../services/kinds.service';
import { Requisition } from '../../../../interfaces/requisition';
import { ProvidersService } from '../../../../services/providers.service';
import { Provider } from '../../../../interfaces/provider';
import { Page } from '../../../../interfaces/page';
import { Budget } from '../../../../interfaces/budget';
import { PurchaseOrder } from '../../../../interfaces/purchase-order';
import { FileUploader } from 'ng2-file-upload';


const swal = require('sweetalert');

@Component({
  selector: 'app-view-purchases',
  templateUrl: './view-purchases.component.html',
  styleUrls: ['./view-purchases.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewPurchasesComponent implements OnInit {

  public presupuesto = 'Presupuesto';
  public uploader: FileUploader = new FileUploader({});
  public purchase:Purchase = {id: null,buyer: "",bill_number: "",with_purchase_order: false,purchase_orders_id:"", providers_name:"",created: "",user: {display_name:"",email:"",phone_number:"",photo_url:"",provider_id:"",uid:"", custom_claims:{}},sector: null, current_state:"Iniciado", states: [{id: null, name: "Iniciado", hours_active:0}],requisitions: [],deliveries: [],budgets: [], documents:[]};
  public currentState:PurchaseState = {id:null,name:"",hours_active:null};
  public providers: Array<Provider>;
  public purchaseDelivery = {id: null, description: "", remit_number:"", created: "", document: {source:""}};
  public selectedProvider:Provider = {id: null,name: "",description: "",address: "",mail: "",contact: "",cuit: "",phone: ""};
  public printing:boolean = false;
  public purchaseOrderToPrint: PurchaseOrder;
  public kindPlanes:[];
  public priorities:any = {1:"Baja", 2:"Media", 3:"Alta"};
  public addingBudget:boolean = false;
  public budgetSelected:Budget;
  @ViewChild('inputUploaderDeliveryDocument') inputUploaderDeliveryDocument: ElementRef;
  constructor(
    private _router:Router,
    private _activatedRoute:ActivatedRoute,
    private purchasesService: PurchasesService,
    private requisitionsService: RequisitionsService,
    private providersService: ProvidersService,
    private kindsService: KindsService,
    private purchaseDeliveriesService: PurchaseDeliveriesService
    ) {
        let id:number = +this._activatedRoute.snapshot.paramMap.get('id');
        this.purchasesService.find(id).subscribe((response:Purchase) => {
          this.purchase = response;

          for (let i = 0; i < this.purchase.budgets.length; i++) {
            this.purchase.budgets[i].purchase_order.items = this.purchase.budgets[i].purchase_order.items.sort( (a, b) => a.item_number - b.item_number);
          }

          let max = 0;
          for(let i = 0; i < this.purchase.states.length; i++) {
            if(this.purchase.states[i].id > max) {
              this.currentState = this.purchase.states[i];
              max = this.purchase.states[i].id;
            }
          }
      });

    }

   ngOnInit(){

      this.providersService.findAll(1000, 1, "name", "ASC").subscribe((response:Page<Provider>) => {
        this.providers = response.content;
      });

      this.kindsService.findAll(10000,1,"name","ASC","purchase_delivery").subscribe((response:any) => {
        this.kindPlanes = response.content;
      });

    }

   public addBudgets(){
    this.addingBudget = true;
    this.purchase.budgets.push({id: null, purchase_order:{id: null, fiscal_condition: "", transport: "", currency: "", other_tax: null, description: "", delivery_address: "Ruta 40 y callejón Padilla 1956 - Chimbas - San Juan", emission_date: null, estimated_delivery_date: null, purchase_condition:null, provider: {id:null, description:"", cuit:"", address:"", name:"", mail:"", contact:"", phone:""}, created: null, items: [{id: null, item_number: null, description: "", amount: null, unit_of_measurement: null, aliquot: null, price:null, created: null }]}, provider: {id: null,name: "",description: "",address: "",mail: "",contact: "",cuit: "",phone: ""}, message: null, selected: false, sent: false, created: null});
   }

   public viewRequisition(id:number){
    this._router.navigate(['requisitions', 'view', id]);
   }

   public unlinkRequisition(requisition:Requisition){
      this.requisitionsService.save(requisition).subscribe(() => {
        for(let i = 0;i < this.purchase.requisitions.length;i++){
          if(this.purchase.requisitions[i].id = requisition.id){
            this.purchase.requisitions.splice(i, 1);
            break;
          }
        }
        swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
      });
   }

   public saveDelivery(delivery:any){
    this.purchaseDeliveriesService.save(delivery, this.purchase.id)
      .subscribe((response:any) => {
          swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
          delivery.id = response.id;
          delivery.source = response.source;
      });

   }

   public deleteDelivery(delivery:any){
    if(delivery.id == null){
         this.purchase.deliveries.pop();
     } else {
         swal({
             title: 'Estás seguro?',
             text: 'El registro se eliminará de forma permanente en el sistema.',
             icon: 'warning',
             buttons: {
                 cancel: {
                   text: 'No, cancelar!',
                   value: null,
                   visible: true,
                   className: "",
                   closeModal: false
               },
               confirm: {
                     text: 'Si, continuar!',
                     value: true,
                     visible: true,
                     className: "bg-danger",
                     closeModal: false
                 }
             }
         }).then((isConfirm) => {
             if (isConfirm) {
                 /** Confirmación  **/
                 this.purchaseDeliveriesService.delete(delivery.id, this.purchase.id).subscribe((response:any) => {
                     swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                     this.purchase.deliveries = this.purchase.deliveries.filter((delivery:any) => delivery.id !== response.id);
                 })
                 /** Confirmación  **/
             } else {
                swal('Cancelado!', 'La acción se canceló :)', 'error');
             }
       });
     }
 }

   public deleteBudget(i:number){
    this.addingBudget = false;
      if(this.purchase.budgets[i].id){
        this.purchase.budgets.splice(i, 1);
        this.purchasesService.save(this.purchase).subscribe((purchase:Purchase) => {
          swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
        });
      } else {
        this.purchase.budgets.splice(i, 1);
      }
   }

   public save() {
      this.addingBudget = false;
      this.purchasesService.save(this.purchase).subscribe((purchase:Purchase) => {
        this.purchase = purchase;
        swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
      });


  }


  public typeaheadProviderOnSelect(selected:any, i:number){
    this.purchase.budgets[i].provider = selected.item;
  }

  public print (){
    setTimeout(() => {
        var tempTitle = document.title;
        document.title = ("00000" + this.purchaseOrderToPrint.id).slice(-5) + " - " + this.purchaseOrderToPrint.provider.name;
        window.print(); document
        .title = tempTitle;
      }, 1000
    );
  }

  public onPurchaseDeliveryDocumentSelected(event: EventEmitter<File[]>){
    const file: File = event[0];
      	var reader  = new FileReader();
      	let delivery =  {id:null, description:file.name, kind:{id: 16, name: "Sin tipo", context: "jobOrders"}, modifing:true, uploading:false, source:null};

      	reader.addEventListener("load", () => {
            delivery.source = reader.result;
            this.purchase.deliveries.push(delivery);
            this.inputUploaderDeliveryDocument.nativeElement.value = '';
      	}, false);

      	reader.addEventListener("error", (event) => {
          	swal('lo sentimos!', 'La operación no pudo ser realizada con exito.', 'error');
      	}, false);

      	reader.readAsDataURL(file);
  }
}
