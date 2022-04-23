import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {orderStates} from '../../../../assets/source-data/states';
import { JobOrdersService } from '../../../services/job-orders.service';
import { State } from '../../../interfaces/state';
import { JobOrder } from '../../../interfaces/job-order';

const swal = require('sweetalert');

@Component({
  selector: 'app-states-job-order',
  templateUrl: './states-job-order.component.html',
  styleUrls: ['./states-job-order.component.scss']
})
export class StatesJobOrderComponent implements OnInit {

  @Input() states:Array<State> = [];

  @Input() currentState:State;

  @Input() jobOrderId:number;

  @Output() jobOrderStates = new EventEmitter<Array<State>>();

  @Output() jobOrderOutput = new EventEmitter<JobOrder>();

  public state:State = {id:null, name:null, hours_active:null};
  public orderStates:Array<State> = orderStates;
  public jobOrder:JobOrder;
  public claims:any;

  constructor(private jobOrdersService:JobOrdersService) { 
    this.claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));
  }

  ngOnInit() { 
  }

  ngOnChanges(): void {
    if(this.jobOrderId)
      this.jobOrdersService.find(this.jobOrderId).subscribe((response:JobOrder) => this.jobOrder = response);
    
  }

  public changeState(modal:any){
    this.jobOrdersService.changeState(this.jobOrderId, this.state).subscribe((response:[]) => {
        this.states = response;
        this.currentState = this.state;
        this.jobOrder.states = this.states;

        if(this.currentState.name == 'Entregado'){
          this.jobOrdersService.save(this.jobOrder).subscribe(() => {
            modal.hide();
            this.jobOrderStates.emit(this.states);
            this.jobOrderOutput.emit(this.jobOrder);
            swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
          });
          
        } else {
          modal.hide();
        }
    });
    
  }


  public dangerStates(){
    let count:number = 0;
    if(this.states != null)
      this.states.forEach((state:State) => {if(state.name == 'En proceso frenado') count++;});
    return count;
  }
  
  public warningStates(){
    let count:number = 0;
    if(this.states != null)
      this.states.forEach((state:State) => {if(state.name == 'Pendiente') count++;});
    return count;
  }
  
  public successStates(){
    let count:number = 0;
    if(this.states != null)
      this.states.forEach((state:State) => {if(state.name == 'En proceso activo') count++;});
    return count;
  }
  
}
