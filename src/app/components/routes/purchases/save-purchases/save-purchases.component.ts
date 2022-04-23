import { Component, OnInit } from '@angular/core';
import { Router} from "@angular/router";
import { Purchase } from '../../../../interfaces/purchase';
import { Sector } from '../../../../interfaces/sector';
import { Page } from '../../../../interfaces/page';
import { PurchasesService } from '../../../../services/purchases.service';
import { SectorsService } from '../../../../services/sectors.service';
const swal = require('sweetalert');

@Component({
  selector: 'app-save-purchases',
  templateUrl: './save-purchases.component.html',
  styleUrls: ['./save-purchases.component.scss']
})
export class SavePurchasesComponent implements OnInit {

  public sectors:Array<Sector> = [];


  public purchase:Purchase = {id: null,buyer: "",bill_number: "",with_purchase_order: false,purchase_orders_id:"", providers_name:"",created: "",user: {display_name:"",email:"",phone_number:"",photo_url:"",provider_id:"",uid:"", custom_claims:{}},sector: null,current_state: "Iniciado", states: [{id: null, name: "Iniciado", hours_active:0}],requisitions: [],deliveries: [],budgets: [],documents:[]};
  constructor(private _router:Router, private purchasesService:PurchasesService, private sectorsService:SectorsService) {
    	this.sectorsService.findAll(10000,1,"name","ASC").subscribe((response:Page<Sector>) => {
        	this.sectors = response.content;
    	});
    }



    ngOnInit() {}

    public viewPurchase(id:number) {
      this._router.navigate(['purchases', 'view', id]);
    }
}
