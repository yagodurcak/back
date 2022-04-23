import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { PurchasesService } from '../../../../services/purchases.service';
import { Purchase } from '../../../../interfaces/purchase';
import { Page } from '../../../../interfaces/page';
const swal = require('sweetalert');

@Component({
  selector: 'app-list-purchases',
  templateUrl: './list-purchases.component.html',
  styleUrls: ['./list-purchases.component.scss']
})
export class ListPurchasesComponent implements OnInit {

  public totalPages:number;
  public totalElements:number;
  public currentPage:number = 1;
  public userPageSize:number = 50;
  public purchases:Array<Purchase>=[];
  public query:string = "";
  public state:string = "";
  public purchase:Purchase = {id: null,buyer: "",bill_number: "",with_purchase_order: false,created: "",purchase_orders_id:"", providers_name:"",user: {display_name:"",email:"",phone_number:"",photo_url:"",provider_id:"",uid:"", custom_claims:{}},sector: null, current_state:"Iniciado", states: [{id: null, name: "Iniciado", hours_active:0}],requisitions: [],deliveries: [],budgets: [], documents:[]};

  constructor(private _router:Router, private purchasesService:PurchasesService) {
    this.findAll();
  }

  ngOnInit() {

  }

  public findAll(){
    this.purchasesService.findAll(this.userPageSize, this.currentPage,"id","DESC", this.state, this.query).subscribe((response:Page<Purchase>) => {
        this.purchases = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
    });
  }

  /**
   * addPurchase
   */
  public addPurchase() {
      this._router.navigate(['purchases', 'save']);
  }

  /**
   * viewPurchase
   */
  public viewPurchase(id:number) {
    this._router.navigate(['purchases', 'view', id]);
  }

  /**
   * deletePurchase
   */
  public deletePurchase(id:number) {

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
            this.purchasesService.delete(id).subscribe((response:Purchase) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                this.purchases = this.purchases.filter(purchase => purchase.id !== response.id);
            })
            /** Confirmación  **/
        } else {
            swal('Cancelado!', 'La acción se canceló :)', 'error');
        }
    });
  }

  public pageChanged(event:any){
    this.currentPage = event.page;
    this.findAll();
  }
}
