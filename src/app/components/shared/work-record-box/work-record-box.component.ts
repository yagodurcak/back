import { Component, OnInit, Input } from '@angular/core';
import { WorkRecord } from '../../../interfaces/work-record';
import { Worker } from '../../../interfaces/worker';
import { JobOrder } from '../../../interfaces/job-order';
import { WorkRecordsService } from '../../../services/work-records.service';
import { Page } from '../../../interfaces/page';
import { WorkersService } from '../../../services/workers.service';
const swal = require('sweetalert');

@Component({
  selector: 'app-work-record-box',
  templateUrl: './work-record-box.component.html',
  styleUrls: ['./work-record-box.component.scss']
})
export class WorkRecordBoxComponent implements OnInit {

  public workRecords:Array<WorkRecord> = [];

  @Input() jobOrder:JobOrder;

  public workRecord:WorkRecord = {id:null, hours:null, worker:null, jobOrder:null, registerDate:null,created: null};

  public worker:Worker = {id:null, name:null, created:null};

  public workers:Array<Worker> = [];

  constructor(private workRecordsService:WorkRecordsService, private workersService:WorkersService) {}

  ngOnInit() {
    if(this.jobOrder && this.jobOrder.id){
        this.workRecordsService.findAll(1000, 1, "registerDate", "DESC", "jobOrder", this.jobOrder.id).subscribe((response:Page<WorkRecord>) => this.workRecords = response.content);
        this.workersService.findAll(1000, 1, "name", "ASC").subscribe((response:Page<Worker>) => this.workers = response.content);;
    }
  }

  /**
   * save
   */
  public save(modal:any) {
        this.workRecord.jobOrder = this.jobOrder;
        this.workRecordsService.save(this.workRecord).subscribe(() => {
            this.workRecordsService.findAll(1000, 1, "registerDate", "DESC", "jobOrder", this.jobOrder.id).subscribe((response:Page<WorkRecord>) => {
                this.workRecords = response.content;
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                modal.hide();
            });
        });
  }

  /**
   * deleteWorkRecord
   */
  public delete(id:number) {

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
            this.workRecordsService.delete(id).subscribe((response:WorkRecord) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                this.workRecords = this.workRecords.filter((workRecord:WorkRecord) => workRecord.id !== response.id);
            })
            /** Confirmación  **/
        } else {
            swal('Cancelado!', 'La acción se canceló :)', 'error');
        }
    });
  }

  /**
   * totalHours
   */
  public totalHours() {
    let totalHours:number = 0;
    for(let i = 0;i < this.workRecords.length;i++)
      totalHours += this.workRecords[i].hours;

      return totalHours;
  }
}
