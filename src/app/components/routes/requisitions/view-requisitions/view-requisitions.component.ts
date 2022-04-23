import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute} from "@angular/router";
import { RequisitionsService } from '../../../../services/requisitions.service';
import { ColorsService } from '../../../shared/colors/colors.service';
import { Requisition } from '../../../../interfaces/requisition';

const swal = require('sweetalert');

@Component({
  selector: 'app-view-requisitions',
  templateUrl: './view-requisitions.component.html',
  styleUrls: ['./view-requisitions.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewRequisitionsComponent implements OnInit {

  public requisition:Requisition = {id: null,description: "",amount: 0,reason: "",unit_of_measurement: "",requesting_date: null,priority: 0,user: null,created: null,requesting_sector: null,destination_sector: null,purchaseId: null, addToPurchase: false};

  constructor(private _activatedRoute:ActivatedRoute, private requisitionsService: RequisitionsService, public colors: ColorsService) {}

   ngOnInit(){
    let id:number = +this._activatedRoute.snapshot.paramMap.get('id');
    this.requisitionsService.find(id).subscribe((response:Requisition) => {
        this.requisition = response;
    });
   }

   public save() {
      this.requisitionsService.save(this.requisition).subscribe(() => {
        swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
      });
  }
}
