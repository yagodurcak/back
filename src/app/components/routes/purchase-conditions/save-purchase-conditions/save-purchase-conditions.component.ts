import { Component, OnInit } from '@angular/core';
import { Router , ActivatedRoute} from '@angular/router';
import { NgForm} from '@angular/forms';
import { PurchaseConditionsService } from '../../../../services/purchase-conditions.service';
import { PurchaseCondition } from '../../../../interfaces/purchase-condition';
const swal = require('sweetalert');

@Component({
  selector: 'app-save-purchase-conditions',
  templateUrl: './save-purchase-conditions.component.html'
})
export class SavePurchaseConditionsComponent implements OnInit {

  public purchaseCondition: PurchaseCondition = {id: null, name: '', created: ''};
  constructor(
    private _router: Router, private _activatedRoute: ActivatedRoute, private purchaseConditionsService: PurchaseConditionsService) { }

  ngOnInit() {
    const id: number = +this._activatedRoute.snapshot.paramMap.get('id');
    if (id !== 0){
      this.purchaseConditionsService.find(id).subscribe((response: PurchaseCondition) => {
        this.purchaseCondition = response;
      });
    }
  }
  /**
   * savePurchaseCondition
   */
  public savePurchaseCondition(data: NgForm) {
    if (data.valid) {
      this.purchaseConditionsService.save(data.value).subscribe(() => {
        swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
        this._router.navigate(['purchaseConditions', 'list']);
      });
    } else{
      swal('Error en los datos!', 'Algunos datos ingresados son incorrectos.', 'error');
    }
  }
}
