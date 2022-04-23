import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { RequisitionsService } from '../../../../services/requisitions.service';
import { Requisition } from '../../../../interfaces/requisition';
import { Purchase } from '../../../../interfaces/purchase';
import { Page } from '../../../../interfaces/page';
import { UserData } from '../../../../interfaces/user-data';
const swal = require('sweetalert');

@Component({
  selector: 'app-list-requisitions',
  templateUrl: './list-requisitions.component.html',
  styleUrls: ['./list-requisitions.component.scss']
})
export class ListRequisitionsComponent implements OnInit {

  public requisitions:Array<Requisition>=[];
  public totalPages:number;
  public totalElements:number;
  public currentPage:number = 1;
  public userPageSize:number = 50;
  public query:string = "";
  public state:string = "Activos";
  public purchase:Purchase = {id: null,buyer: "",bill_number: "",with_purchase_order: false,purchase_orders_id:"", providers_name:"",created: "",user: {display_name:"",email:"",phone_number:"",photo_url:"",provider_id:"",uid:"", custom_claims:{}},sector: null,current_state:"Iniciado", states: [{id: null, name: "Iniciado", hours_active:0}],requisitions: [],deliveries: [],budgets: [], documents:[]};
  public priorities:any = {1:"Baja", 2:"Media", 3:"Alta"};
  public claims:any;
  public user:any;

  constructor(private _router:Router, private requisitionsService:RequisitionsService) {
    this.claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));
    this.user = JSON.parse(localStorage.getItem('indumet-workload-user'));
    if(!this.claims['admin'])
      this.state = 'Pendiente';
    this.findAll();
  }

  ngOnInit() {

  }

  public findAll(){
    this.requisitionsService.findAll(this.userPageSize,this.currentPage,"id","DESC", this.state).subscribe((response:Page<Requisition>) => {
        this.requisitions = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
        this.requisitions.forEach(requisition => {if(requisition.purchaseId == null)requisition.addToPurchase = false;});
        if(!this.claims['admin'] && !this.claims['purchase'] && this.claims['storeroom'])
        this.requisitions = this.requisitions.filter(requisition => requisition.user.uid === this.user.providerData[0].uid)
    });
  }

  public addToPurchase(){
    for(let i = 0;i < this.requisitions.length;i++)
      if(this.requisitions[i].addToPurchase)
        return true;
    return false;
  }

  /**
   * addJob
   */
  public addRequisition() {
      this._router.navigate(['requisitions', 'save']);
  }

  /**
   * viewJob
   */
  public viewRequisition(id:number) {
    this._router.navigate(['requisitions', 'view', id]);
  }

  public viewPurchase(id:number) {
    this._router.navigate(['purchases', 'view', id]);
  }

  /**
   * deleteJob
   */
  public deleteRequisition(id:number) {

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
            this.requisitionsService.delete(id).subscribe((response:Requisition) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                this.requisitions = this.requisitions.filter(requisition => requisition.id !== response.id);
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
