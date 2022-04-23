import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PurchaseConditionsService } from '../../../../services/purchase-conditions.service';
import { PurchaseCondition } from '../../../../interfaces/purchase-condition';
import { Page } from '../../../../interfaces/page';
const swal = require('sweetalert');

@Component({
  selector: 'app-list-purchase-conditions',
  templateUrl: './list-purchase-conditions.component.html'
})
export class ListPurchaseConditionsComponent implements OnInit {

  public purchaseConditions: Array<PurchaseCondition> = [];
  constructor(private _router: Router, private purchaseConditionsService: PurchaseConditionsService) {
      this.purchaseConditionsService.findAll(10000, 1, 'name', 'ASC').subscribe((response: Page<PurchaseCondition>) => {
        this.purchaseConditions = response.content;
    });
  }

  ngOnInit() {
  }

  /**
   * addPurchaseCondition
   */
  public addPurchaseCondition() {
      this._router.navigate(['purchaseConditions', 'save', 0]);
  }

  /**
   * editPurchaseCondition
   */
  public editPurchaseCondition(id:number) {
    this._router.navigate(['purchaseConditions', 'save', id]);
  }

  /**
   * deletePurchaseCondition
   */
  public deletePurchaseCondition(id) {

    swal({
        title: 'Estás seguro?',
        text: 'El registro se eliminará de forma permanente en el sistema.',
        icon: 'warning',
        buttons: {
            cancel: {
                text: 'No, cancelar!',
                value: null,
                visible: true,
                className: '',
                closeModal: false
            },
            confirm: {
                text: 'Si, continuar!',
                value: true,
                visible: true,
                className: 'bg-danger',
                closeModal: false
            }
        }
    }).then((isConfirm) => {
        if (isConfirm) {
            /** Confirmación  **/
            this.purchaseConditionsService.delete(id).subscribe((response:PurchaseCondition) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                this.purchaseConditions = this.purchaseConditions.filter(purchaseCondition => purchaseCondition.id !== response.id);
            })
            /** Confirmación  **/
        } else {
            swal('Cancelado!', 'La acción se canceló :)', 'error');
        }
    });
  }
}
