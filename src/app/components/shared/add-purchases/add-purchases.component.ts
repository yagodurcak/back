import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { PurchasesService } from '../../../services/purchases.service';
import { SectorsService } from '../../../services/sectors.service';
import { Purchase } from '../../../interfaces/purchase';
import { Sector } from '../../../interfaces/sector';
import { Page } from '../../../interfaces/page';
import { Requisition } from '../../../interfaces/requisition';
const swal = require('sweetalert');
@Component({
  selector: 'app-add-purchases',
  templateUrl: './add-purchases.component.html',
  styleUrls: ['./add-purchases.component.scss']
})
export class AddPurchasesComponent implements OnInit {

  @Output() savedPurchase = new EventEmitter<Purchase>();
	@Input() purchase:Purchase;
	@Input() requisitions:Array<Requisition>;
  
	public sectors:Array<Sector> = [];
  
  constructor(private purchasesService:PurchasesService, private sectorsService:SectorsService) {
    	this.sectorsService.findAll(10000,1,"name","ASC").subscribe((response:Page<Sector>) => {
        	this.sectors = response.content;
    	});
	}

  ngOnInit() {
  }  
  /**
   * savePurchase
   */
  	public addPurchase() {
			this.purchase.requisitions = [];
			this.requisitions.forEach((requisition) => requisition.addToPurchase && this.purchase.requisitions.push(requisition));
			this.purchase.user.display_name = JSON.parse(localStorage.getItem("indumet-workload-user")).providerData[0].displayName;
      this.purchase.user.email = JSON.parse(localStorage.getItem("indumet-workload-user")).providerData[0].email;
      this.purchase.user.phone_number = JSON.parse(localStorage.getItem("indumet-workload-user")).providerData[0].phoneNumber;
      this.purchase.user.photo_url = JSON.parse(localStorage.getItem("indumet-workload-user")).providerData[0].photoURL;
      this.purchase.user.provider_id = JSON.parse(localStorage.getItem("indumet-workload-user")).providerData[0].providerId;
      this.purchase.user.uid = JSON.parse(localStorage.getItem("indumet-workload-user")).providerData[0].uid;
    	this.purchasesService.save(this.purchase).subscribe(
			(response:Purchase) => {
			    swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
			    this.savedPurchase.emit(response);
			},
			(response:any) => {
				if(response.error.message == 'Duplicate item or number field.')
				swal('Datos de entrada incorrectos!', 'El número del trabajo o de item ya existen para otro.', 'error');
			}
		);
  	} 
}
