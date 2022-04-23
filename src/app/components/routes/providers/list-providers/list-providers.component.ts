import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { ProvidersService } from '../../../../services/providers.service';
import { Provider } from '../../../../interfaces/provider';
import { Page } from '../../../../interfaces/page';
const swal = require('sweetalert');
@Component({
  selector: 'app-list-providers',
  templateUrl: './list-providers.component.html',
  styleUrls: ['./list-providers.component.scss']
})
export class ListProvidersComponent implements OnInit {

  public providers:Array<Provider>=[];
  
  constructor(private _router:Router, private providerService:ProvidersService) { 
      this.providerService.findAll(10000,1,"name","ASC").subscribe((response:Page<Provider>) => {
        this.providers = response.content;
    });
  }

  ngOnInit() {
   
  }

  /**
   * addProvider
   */
  public addProvider() {
      this._router.navigate(['providers', 'save', 0]);
  }

  /**
   * editProvider
   */
  public editProvider(id:number) {
    this._router.navigate(['providers', 'save', id]);
  }

  /**
   * deleteProvider
   */
  public deleteProvider(id) {

    swal({
        title: 'Estás seguro?',
        text: 'El registro se eliminará de forma permanente en el sistema.',
        icon: 'warning',
        buttons: {
            cancel: {
                text: 'No, cancelar!',
                value: null,
                visible: true,
                className: "",
                closeModal: false
            },
            confirm: {
                text: 'Si, continuar!',
                value: true,
                visible: true,
                className: "bg-danger",
                closeModal: false
            }
        }
    }).then((isConfirm) => {
        if (isConfirm) {
            /** Confirmación  **/
            this.providerService.delete(id).subscribe((response:Provider) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                this.providers = this.providers.filter(provider => provider.id !== response.id);
            })
            /** Confirmación  **/
        } else {
            swal('Cancelado!', 'La acción se canceló :)', 'error');
        }
    });
  }

}
