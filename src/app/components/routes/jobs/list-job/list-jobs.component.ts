import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { JobsService } from '../../../../services/jobs.service';
import { Page } from '../../../../interfaces/page';
import { Job } from '../../../../interfaces/job';
import { ExcelService } from '../../../../services/excel.service';
import {ColorsService} from '../../../shared/colors/colors.service';
const swal = require('sweetalert');

@Component({
  selector: 'app-list-jobs',
  templateUrl: './list-jobs.component.html',
  styleUrls: ['./list-jobs.component.scss']
})
export class ListJobsComponent implements OnInit {

  public jobs:Array<Job>=[];
  public userPageSize:number = 50;
  public totalPages:number;
  public totalElements:number;
  public currentPage:number = 1;
  public query:string = "";
  public claims:any;
  csvData = [];
  csvOptions = {
    fieldSeparator: ',',
    quoteStrings: '"',
    decimalseparator: '.',
    showLabels: true,
    showTitle: true,
    title: 'Listado de trabajos',
    useBom: true,
    noDownload: false,
    headers: [
      "Código",
      "Número",
      "Item",
      "Descripción",
      "Tipo"
    ]
  };

  constructor(private _router: Router, private jobsService: JobsService, private excelService: ExcelService) {
    this.findAll();
    this.claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));
  }

  ngOnInit() {

  }

  public findAll(){
    this.jobsService.findAll(this.userPageSize,this.currentPage,"id","DESC", this.query).subscribe((response:Page<Job>) => {
        this.jobs = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;

      this.csvData = [];
      for(let i = 0;i < this.jobs.length;i++){
        this.csvData.push({
          "Código": this.jobs[i].id,
          "Número": this.jobs[i].number,
          "Item": this.jobs[i].item,
          "Descripción": this.jobs[i].description,
          "Tipo": this.jobs[i].kind.name
        });
      }
    });
  }

  /**
   * addJob
   */
  public addJob() {
      this._router.navigate(['jobs', 'save']);
  }

  /**
   * viewJob
   */
  public viewJob(id) {
    this._router.navigate(['jobs', 'view', id]);
  }

  /**
   * deleteJob
   */
  public deleteJob(id:number) {

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
            this.jobsService.delete(id).subscribe((response:Job) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                this.jobs = this.jobs.filter(job => job.id !== response.id);
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

  exportAsXLSX():void {
    this.excelService.exportAsExcelFile(this.csvData, 'Listado de Trabajos');
  }
}
