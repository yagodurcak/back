import { Component, OnInit } from '@angular/core';
import { Router} from "@angular/router";
import { JobOrder } from '../../../../interfaces/job-order';

@Component({
  selector: 'app-save-job-orders',
  templateUrl: './save-job-orders.component.html',
  styleUrls: ['./save-job-orders.component.scss']
})
export class SaveJobOrdersComponent implements OnInit {
    constructor(private _router:Router) { }
    
    ngOnInit() {}

    public toJobOrderView(jobOrder:JobOrder){
      this._router.navigate(['job-orders', 'view', jobOrder.id]);
    }

}
