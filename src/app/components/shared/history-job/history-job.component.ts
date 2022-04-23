import { Component, OnInit, Input } from '@angular/core';
import { Router } from "@angular/router";
import { Job } from '../../../interfaces/job';
import { JobOrder } from '../../../interfaces/job-order';
import { JobsService } from '../.././../services/jobs.service';
import { ColorsService } from '../../shared/colors/colors.service';
import { ExcelService } from '../../../services/excel.service';


@Component({
  selector: 'app-history-job',
  templateUrl: './history-job.component.html',
  styleUrls: ['./history-job.component.scss']
})
export class HistoryJobComponent implements OnInit {
  
  @Input() job:Job;

  public years:Array<number> = [];
  public yearSelected:number;
  public jobOrders:Array<JobOrder>=[];
  public init:boolean = true;
  sparkOptionPie = {
    type: 'pie',
    width: '2em',
    height: '2em',
    sliceColors: [this.colors.byName('success'), this.colors.byName('gray-light')]
  };
  csvData = [];
  csvOptions = { 
    fieldSeparator: ',',
    quoteStrings: '"',
    decimalseparator: '.',
    showLabels: true, 
    showTitle: true,
    title: 'Historial de trabajo:',
    useBom: true,
    noDownload: false,
    headers: [
      "Identificador",
      "N° de orden de compra",
      "Fecha de entrada",
      "Fecha de compromiso con el cliente",
      "Fecha de despacho",
      "Cantidad",
      "Horas cotizadas",
      "Porcentaje de avance",
      "Especificaciones técnicas",
      "N° de factura",
      "N° de remito",
      "Horas reales de producción",
      "Observaciones",
      "Estado",
      "Tipo"
    ]
  };
  
  constructor(private _router:Router, private jobsService:JobsService, private colors: ColorsService, private excelService:ExcelService) { }

  ngOnInit() {
    this.findAll(this.job.id);
  }

  public findAll(id:number, year?:number, month?:number){
    this.jobsService.findJobOrders(id, year, month).subscribe((response:Array<JobOrder>) => {
        this.jobOrders = response;

        if(this.init == true){
          for(let i = 0;i < this.jobOrders.length;i++){
            let dateSplited:Array<string> = this.jobOrders[i].in_date.split("/");
            if(dateSplited.length == 3){
              let date:Date = new Date(dateSplited[2] + "-" + dateSplited[1] + "-" + dateSplited[0]);
              if(date.getFullYear() != NaN && this.years.indexOf(date.getFullYear()) == -1)
                this.years.push(date.getFullYear());
            }
          }
          this.init = false;
        }

        this.csvData = [];
        for(let i = 0;i < this.jobOrders.length;i++){
          this.csvData.push({
            "OT": this.jobOrders[i].id,
            "Número de orden de compra": this.jobOrders[i].purchase_order_number,
            "Fecha de entrada": this.jobOrders[i].in_date,
            "Fecha de compromiso": this.jobOrders[i].compromised_date,
            "Fecha de despacho": this.jobOrders[i].delivery_date,
            "Cantidad": this.jobOrders[i].jobs_amount,
            "Horas cotizadas": this.jobOrders[i].budgeted_hours,
            "Porcentaje de avance": this.jobOrders[i].percentage_advance,
            "Descripción": this.jobOrders[i].description,
            "Numero de factura": this.jobOrders[i].bill_number,
            "Número de remito": this.jobOrders[i].remit_number,
            "Horas reales de producción": this.jobOrders[i].real_hours_production,
            "Observaciones": this.jobOrders[i].observations,
            "Estado": this.jobOrders[i].current_state,
            "Tipo": this.jobOrders[i].kind.name,
          });
        }
    });
  }
  /**
   * viewJob
   */
  public viewJobOrder(id:number) {
    this._router.navigate(['jobOrders', 'view', id]);
  }

  exportAsXLSX():void {
    this.excelService.exportAsExcelFile(this.csvData, 'Historial de Trabajo');
  }

}
