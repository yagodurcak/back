import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { WorkersService } from '../../../../services/workers.service';
import { Worker } from '../../../../interfaces/worker';
import { Page } from '../../../../interfaces/page';
const swal = require('sweetalert');

@Component({
  selector: 'app-list-workers',
  templateUrl: './list-workers.component.html'
})
export class ListWorkersComponent implements OnInit {

  public workers:Array<Worker>=[];
  
  constructor(private _router:Router, private workerService:WorkersService) { 
      this.workerService.findAll(10000,1,"name","ASC").subscribe((response:Page<Worker>) => {
        this.workers = response.content;
    });
  }

  ngOnInit() {
   
  }

  /**
   * addWorker
   */
  public addWorker() {
      this._router.navigate(['workers', 'save', 0]);
  }

  /**
   * editWorker
   */
  public editWorker(id:number) {
    this._router.navigate(['workers', 'save', id]);
  }

  /**
   * deleteWorker
   */
  public deleteWorker(id) {

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
            this.workerService.delete(id).subscribe((response:Worker) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                this.workers = this.workers.filter(worker => worker.id !== response.id);
            })
            /** Confirmación  **/
        } else {
            swal('Cancelado!', 'La acción se canceló :)', 'error');
        }
    });
  }
}
