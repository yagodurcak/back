import { Component, OnInit, Input, SimpleChanges } from '@angular/core';
import {purchaseStates} from '../../../../assets/source-data/states';
import { PurchasesService } from '../../../services/purchases.service';
import { PurchaseState } from '../../../interfaces/purchase-state';
import { State } from '../../../interfaces/state';
import { Purchase } from '../../../interfaces/purchase';

const swal = require('sweetalert');

@Component({
  selector: 'app-states-purchase',
  templateUrl: './states-purchase.component.html',
  styleUrls: ['./states-purchase.component.scss']
})
export class StatesPurchaseComponent implements OnInit {

  @Input() states:Array<State> = [];

  @Input() purchaseId:number;

  @Input() currentState:PurchaseState;

  public state:State = {id:null, name:null, hours_active:null};
  public purchaseStates:Array<State> = purchaseStates;
  public purchase:Purchase;

  constructor(private purchasesService:PurchasesService) { }

  ngOnInit() { 
  }

  ngOnChanges(): void {
    if(this.purchaseId)
      this.purchasesService.find(this.purchaseId).subscribe((response:Purchase) => this.purchase = response);
    
  }

  public changeState(modal:any){
    this.purchasesService.changeState(this.purchaseId, this.state).subscribe((response:[]) => {
        this.states = response;
        this.currentState = this.state;
        modal.hide();
    });
    
  }
}
