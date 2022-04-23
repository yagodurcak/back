import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { SectorsService } from '../../../../services/sectors.service';
import { Sector } from '../../../../interfaces/sector';
import { Page } from '../../../../interfaces/page';
const swal = require('sweetalert');

@Component({
  selector: 'app-list-sectors',
  templateUrl: './list-sectors.component.html',
  styleUrls: ['./list-sectors.component.scss']
})
export class ListSectorsComponent implements OnInit {

  public sectors:Array<Sector>=[];
  
  constructor(private _router:Router, private sectorService:SectorsService) { 
      this.sectorService.findAll(10000,1,"name","ASC").subscribe((response:Page<Sector>) => {
        this.sectors = response.content;
    });
  }

  ngOnInit() {
   
  }

  /**
   * addSector
   */
  public addSector() {
      this._router.navigate(['sectors', 'save', 0]);
  }

  /**
   * editSector
   */
  public editSector(id:number) {
    this._router.navigate(['sectors', 'save', id]);
  }

  /**
   * deleteSector
   */
  public deleteSector(id) {

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
            this.sectorService.delete(id).subscribe((response:Sector) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                this.sectors = this.sectors.filter(sector => sector.id !== response.id);
            })
            /** Confirmación  **/
        } else {
            swal('Cancelado!', 'La acción se canceló :)', 'error');
        }
    });
  }

}
