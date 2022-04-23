import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { JobOrdersService } from '../../../../services/job-orders.service';
import { ColorsService } from '../../../shared/colors/colors.service';
const swal = require('sweetalert');
import * as moment from 'moment';
import { JobOrder } from '../../../../interfaces/job-order';
import { Page } from '../../../../interfaces/page';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  public jobOrders:Array<JobOrder>=[];
  public query:string;
  public state:string = "En proceso activo,En proceso frenado,Pendiente,Terminado";
  sparkOptionPie = {
        type: 'pie',
        width: '2em',
        height: '2em',
        sliceColors: [this.colors.byName('success'), this.colors.byName('gray-light')]
    };

  constructor(private _router:Router, private jobOrdersService:JobOrdersService, private colors: ColorsService) {
    this.findAll();
  }

  ngOnInit() {

  }

  public isBackward(date:string){
    return moment(date, "DD/MM/YYYY").isBefore(moment(new Date()))

  }

  public findAll(){
    this.jobOrdersService.findAll(100000,1,"compromisedDate,id","ASC,DESC", this.state, "").subscribe((response:Page<JobOrder>) => {
        this.jobOrders = response.content;
        for(let i = 0;i < this.jobOrders.length;i++){
          for(let j = 0;j < this.jobOrders[i].job.pictures.length;j++){
            this.jobOrders[i].job.pictures[j].backgroundImage = "url(" + this.jobOrders[i].job.pictures[j].thumbnail +")";
          }
        }
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

  public print(){
    setTimeout(() => {
        var tempTitle = document.title;
        let d = new Date();
        document.title = "Planilla principal - " + d.getDate() + "/" + d.getMonth() + "/" + d.getFullYear();
        window.print();
        document.title = tempTitle;
      }, 1000
    );

  }
}
