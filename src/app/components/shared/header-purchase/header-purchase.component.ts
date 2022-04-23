import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { SectorsService } from '../../../services/sectors.service';
import { PurchasesService } from '../../../services/purchases.service';
import { Sector } from '../../../interfaces/sector';
import { Purchase } from '../../../interfaces/purchase';
import { Page } from '../../../interfaces/page';

const swal = require('sweetalert');

@Component({
  selector: 'app-header-purchase',
  templateUrl: './header-purchase.component.html',
  styleUrls: ['./header-purchase.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class HeaderPurchaseComponent implements OnInit {

  @Input() purchase:Purchase;
  public sectors:Array<Sector> = [];
  public sectorsName = []

  constructor(public sectorsService:SectorsService, public purchasesService:PurchasesService) { }

  ngOnInit() {
    this.sectorsService.findAll(10000,1,"name","ASC").subscribe((response:Page<Sector>) => {
      this.sectors = response.content;
      this.sectorsName = this.sectors.map((sector:Sector) => {return {value:sector.name, text:sector.name}});
    });
  }

  public save(event:any, field?:string){
    this.purchasesService.save(this.purchase).subscribe(() => {
      swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
    });
  
  }

  public saveSector(event:any, field?:string){
    this.sectors.forEach((elem) => {
      if(elem.name == event){
        this.purchase.sector = elem;
        return false;
      }
    });
    this.purchasesService.save(this.purchase).subscribe(() => {
      swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
    });
  
  }

}
