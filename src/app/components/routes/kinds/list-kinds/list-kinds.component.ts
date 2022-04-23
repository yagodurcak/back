import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { KindsService } from '../../../../services/kinds.service';
import { Contexts, contexts } from '../contexts';
import { Kind } from '../../../../interfaces/kind';
import { Page } from '../../../../interfaces/page';
import { Context } from '../../../../interfaces/context';
const swal = require('sweetalert');

@Component({
  selector: 'app-list-kinds',
  templateUrl: './list-kinds.component.html'
})
export class ListKindsComponent implements OnInit {

  public kinds:Array<Kind>=[];
  public contexts:Array<Context> = Contexts;
  
  constructor(private _router:Router, private kindService:KindsService) { 
      this.kindService.findAll(10000,1,"name","ASC","all").subscribe((response:Page<Kind>) => {
        this.kinds = response.content;
    });
  }

  ngOnInit() {
   
  }

  /**
   * addKind
   */
  public addKind() {
      this._router.navigate(['kinds', 'save', 0]);
  }

  /**
   * editKind
   */
  public editKind(id:number) {
    this._router.navigate(['kinds', 'save', id]);
  }

  /**
   * deleteKind
   */
  public deleteKind(id) {

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
            this.kindService.delete(id).subscribe((response:Kind) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                this.kinds = this.kinds.filter(kind => kind.id !== response.id);
            })
            /** Confirmación  **/
        } else {
            swal('Cancelado!', 'La acción se canceló :)', 'error');
        }
    });

    
  }
  
}
