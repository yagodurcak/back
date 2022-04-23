import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { JobOrdersService } from '../../../services/job-orders.service';
import { KindsService } from '../../../services/kinds.service';
import {orderStates} from '../../../../assets/source-data/states';
import { JobOrder } from '../../../interfaces/job-order';
import { Kind } from '../../../interfaces/kind';
import { State } from '../../../interfaces/state';
import { Page } from '../../../interfaces/page';
const swal = require('sweetalert');

@Component({
  selector: 'app-add-job-order',
  templateUrl: './add-job-order.component.html',
  styleUrls: ['./add-job-order.component.scss']
})
export class AddJobOrderComponent implements OnInit {

    @Output() savedJobOrder = new EventEmitter<JobOrder>();
    public jobOrder:JobOrder = {id:null, description:"", kind:null, job:{id:null, number:"", item:"", description:"", kind:null, components:[], pictures:[], planes:[], documents:[], searchField:null}, purchase_order_number:"",in_date:"",compromised_date:"",delivery_date:null,jobs_amount:0,states:[{id:null, name:"", hours_active:0}],bill_number:"",remit_number:"",observations:"", current_state:"",budgeted_hours: 0,percentage_advance: 0,real_hours_production: null, documents:[]};
    public kinds:Array<Kind> = [];
    public orderStates:Array<State> = orderStates;
    public state_index:number;
  
    // Datepicker
    bsValue = new Date();
    bsConfig = {
        containerClass: 'theme-angle'
    }
  
    constructor(private jobOrdersService:JobOrdersService, private kindsService:KindsService) {
        this.state_index = 0;
        this.kindsService.findAll(10000,1,"name","ASC","jobOrders").subscribe((response:Page<Kind>) => {
            this.kinds = response.content;
        });
    }

    ngOnInit() { }  
	public save() {
        if(this.jobOrder.states.length > 0){
            this.jobOrder.current_state = this.jobOrder.states[this.jobOrder.states.length - 1].name;
            this.jobOrdersService.save(this.jobOrder).subscribe((response:JobOrder) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                this.savedJobOrder.emit(response);
            });
        } else {
            swal('Mmmm algo anda mal!', 'El estaod de la orden de trabajo no es correcto.', 'error');
        }
    }

    public cleanJob(){
        this.jobOrder.job = {id:null, number:null, item:null, description:null, kind:null, components:[], pictures:[], planes:[], documents:[], searchField:null};
    }

}
