import { Component, OnInit } from '@angular/core';
import { Router} from "@angular/router";
import { Requisition } from '../../../../interfaces/requisition';
import { Sector } from '../../../../interfaces/sector';
import { Page } from '../../../../interfaces/page';
import { RequisitionsService } from '../../../../services/requisitions.service';
import { SectorsService } from '../../../../services/sectors.service';
const swal = require('sweetalert');

@Component({
  selector: 'app-save-requisitions',
  templateUrl: './save-requisitions.component.html',
  styleUrls: ['./save-requisitions.component.scss']
})
export class SaveRequisitionsComponent implements OnInit {

  public sectors:Array<Sector> = [];
  

  public requisition:Requisition = {id: null,description: "",amount: 0,reason: "",requesting_date: null,priority: 0, unit_of_measurement: '', user: {display_name:"",email:"",phone_number:"",photo_url:"",provider_id:"",uid:"", custom_claims:{}},created: null,requesting_sector: null,destination_sector: null,purchaseId: null, addToPurchase:false};
    constructor(private _router:Router, private requisitionsService:RequisitionsService, private sectorsService:SectorsService) { 
    	this.sectorsService.findAll(10000,1,"name","ASC").subscribe((response:Page<Sector>) => {
        	this.sectors = response.content;
    	});
    }

    
    
    ngOnInit() {}

    public save() {
      this.requisition.user.display_name = JSON.parse(localStorage.getItem("indumet-workload-user")).providerData[0].displayName;
      this.requisition.user.email = JSON.parse(localStorage.getItem("indumet-workload-user")).providerData[0].email;
      this.requisition.user.phone_number = JSON.parse(localStorage.getItem("indumet-workload-user")).providerData[0].phoneNumber;
      this.requisition.user.photo_url = JSON.parse(localStorage.getItem("indumet-workload-user")).providerData[0].photoURL;
      this.requisition.user.provider_id = JSON.parse(localStorage.getItem("indumet-workload-user")).providerData[0].providerId;
      this.requisition.user.uid = JSON.parse(localStorage.getItem("indumet-workload-user")).providerData[0].uid;
      this.requisitionsService.save(this.requisition).subscribe((response:Requisition) => {
          swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
          this._router.navigate(['requisitions', 'list']);
      });
  }
}
