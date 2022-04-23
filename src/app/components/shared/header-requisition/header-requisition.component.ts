import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { SectorsService } from '../../../services/sectors.service';
import { RequisitionsService } from '../../../services/requisitions.service';
import { Sector } from '../../../interfaces/sector';
import { Requisition } from '../../../interfaces/requisition';
import { Page } from '../../../interfaces/page';

const swal = require('sweetalert');

@Component({
  selector: 'app-header-requisition',
  templateUrl: './header-requisition.component.html',
  styleUrls: ['./header-requisition.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class HeaderRequisitionComponent implements OnInit {

  @Input() requisition:Requisition;
  public sectors:Array<Sector> = [];
  public sectorsName = []


  constructor(public sectorsService:SectorsService, public requisitionsService:RequisitionsService) {}

  ngOnInit() {
    this.sectorsService.findAll(10000,1,"name","ASC").subscribe((response:Page<Sector>) => {
      this.sectors = response.content;
      this.sectorsName = this.sectors.map((sector:Sector) => {return {value:sector.name, text:sector.name}});
    });
  }

  public save(event:any, field?:string){
    this.requisitionsService.save(this.requisition).subscribe(() => {
      swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
    });
  
  }

  public saveRequestingSector(event:any, field?:string){
    this.sectors.forEach((elem) => {
      if(elem.name == event){
        this.requisition.requesting_sector = elem;
        return false;
      }
    });
    this.requisitionsService.save(this.requisition).subscribe(() => {
      swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
    });
  
  }

  public saveDesitinationSector(event:any, field?:string){
    this.sectors.forEach((elem) => {
      if(elem.name == event){
        this.requisition.destination_sector = elem;
        return false;
      }
    });
    this.requisitionsService.save(this.requisition).subscribe(() => {
      swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
    });
  
  }
}
