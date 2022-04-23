import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { KindsService } from '../../../services/kinds.service';
import { JobOrdersService } from '../../../services/job-orders.service';
import { Kind } from '../../../interfaces/kind';
import { JobOrder } from '../../../interfaces/job-order';
import { Page } from '../../../interfaces/page';

const swal = require('sweetalert');

@Component({
  selector: 'app-header-job-order',
  templateUrl: './header-job-order.component.html',
  styleUrls: ['./header-job-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class HeaderJobOrderComponent implements OnInit {

  @Input() jobOrder:JobOrder;
  public kinds:Array<Kind> = [];
  public kindsName = [];

  public claims:any;

  constructor(public kindsService:KindsService, public jobOrdersService:JobOrdersService) { 
    this.claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));
  }

  ngOnInit() {
    this.kindsService.findAll(10000,1,"name","ASC","jobOrders").subscribe((response:Page<Kind>) => {
      this.kinds = response.content;
      this.kindsName = this.kinds.map((kind:Kind) => {return {value:kind.name, text:kind.name}});
    });
  }

  public save(event:any, field?:string){
    if(field == 'description'){
        this.jobOrder.kind = this.kinds.filter((kind:Kind) => kind.name == this.jobOrder.kind.name)[0];
    }
    this.jobOrdersService.save(this.jobOrder).subscribe(() => {
      swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
    });
  
  }

}
