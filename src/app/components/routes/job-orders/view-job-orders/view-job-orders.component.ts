import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute} from "@angular/router";
import { JobOrdersService } from '../../../../services/job-orders.service';
import { ColorsService } from '../../../shared/colors/colors.service';
import { JobOrder } from '../../../../interfaces/job-order';
import { State } from '../../../../interfaces/state';

const swal = require('sweetalert');

@Component({
  selector: 'app-view-job-orders',
  templateUrl: './view-job-orders.component.html',
  styleUrls: ['./view-job-orders.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewJobOrdersComponent implements OnInit {

  public job_order_percentage_advance_edit:boolean = false;
  public jobOrder:JobOrder = {id:null, description:"", kind:null, job:null, purchase_order_number:"",in_date:"",compromised_date:"",delivery_date:null,jobs_amount:0,states:[{id:null, name:"", hours_active:0}],bill_number:"",remit_number:"",observations:"", current_state:"",budgeted_hours: null,percentage_advance: 0,real_hours_production: null, documents:[]};
  public currentState:State = {id:null,name:"",hours_active:null};
  public states:Array<State> = [];
  public pieOptions = {
    animate: {
        duration: 800,
        enabled: true
    },
    barColor: this.colors.byName('info'),
    trackColor: 'rgba(200,200,200,0.4)',
    scaleColor: false,
    lineWidth: 10,
    lineCap: 'round',
    size: 145
  };
  public claims:any;

  
  constructor(private _activatedRoute:ActivatedRoute, private jobOrdersService: JobOrdersService, public colors: ColorsService) {
    this.claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));  
  }

   ngOnInit(){
    let id:number = +this._activatedRoute.snapshot.paramMap.get('id');
    this.jobOrdersService.find(id).subscribe((response:JobOrder) => {
        this.jobOrder = response;
        let max = 0;
        this.states = this.jobOrder.states;
        for(let i = 0;i < this.jobOrder.states.length;i++){
          if(this.jobOrder.states[i].id > max){
            this.currentState = this.jobOrder.states[i];
            max = this.jobOrder.states[i].id;
          }
        }
    });
   }

   public save() {
    if(this.jobOrder.states.length > 0){
      this.jobOrder.current_state = this.jobOrder.states[this.jobOrder.states.length - 1].name;
      this.jobOrdersService.save(this.jobOrder).subscribe(() => {
        swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
      });
    } else {
        swal('Mmmm algo anda mal!', 'El estaod de la orden de trabajo no es correcto.', 'error');
    }
    
  }

  public updatejobOrder(jobOrder:JobOrder){
    this.jobOrder = jobOrder;
  }
}
