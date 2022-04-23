import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { JobOrdersService } from '../../../../services/job-orders.service';
import { JobOrder } from '../../../../interfaces/job-order';
import { Page } from '../../../../interfaces/page';
import { ColorsService } from '../../../shared/colors/colors.service';
const swal = require('sweetalert');

@Component({
  selector: 'app-list-job-orders',
  templateUrl: './list-job-orders.component.html',
  styleUrls: ['./list-job-orders.component.scss']
})
export class ListJobOrdersComponent implements OnInit {

  public jobOrders:Array<JobOrder>=[];
  public userPageSize:number = 50;
  public totalPages:number;
  public totalElements:number;
  public currentPage:number = 1;
  public query:string = "";
  public state:string = "";
  sparkOptionPie = {
        type: 'pie',
        width: '2em',
        height: '2em',
        sliceColors: [this.colors.byName('success'), this.colors.byName('gray-light')]
    };
    public claims:any;
  
  constructor(private _router:Router, private jobOrdersService:JobOrdersService, private colors: ColorsService) { 
    this.claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));
    if(!this.claims['admin'])
      this.state = 'Pendiente';
    this.findAll();
  }

  ngOnInit() {
   
  }

  public findAll(){
    this.jobOrdersService.findAll(this.userPageSize,this.currentPage,"id","DESC", this.state, this.query).subscribe((response:Page<JobOrder>) => {
        this.jobOrders = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
    });
  }

  /**
   * addJob
   */
  public addJobOrder() {
      this._router.navigate(['jobOrders', 'save']);
  }

  /**
   * viewJob
   */
  public viewJobOrder(id:number) {
    this._router.navigate(['jobOrders', 'view', id]);
  }

  /**
   * deleteJob
   */
  public deleteJobOrder(id:number) {

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
            this.jobOrdersService.delete(id).subscribe((response:JobOrder) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                this.jobOrders = this.jobOrders.filter(jobOrder => jobOrder.id !== response.id);
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
