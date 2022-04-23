import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute} from "@angular/router";
import { Sector } from '../../../../interfaces/sector';
import { SectorsService } from '../../../../services/sectors.service';
const swal = require('sweetalert');

@Component({
  selector: 'app-save-sectors',
  templateUrl: './save-sectors.component.html',
  styleUrls: ['./save-sectors.component.scss']
})
export class SaveSectorsComponent implements OnInit {

  public sector:Sector = {id: null,name: ""};

  constructor(private _router:Router, private sectorsService:SectorsService, private _activatedRoute:ActivatedRoute) {}
  ngOnInit() {
    let id:number = +this._activatedRoute.snapshot.paramMap.get('id');
    if(id != 0){
      this.sectorsService.find(id).subscribe((response:Sector) => {
        this.sector = response;
      });
    }
  }
    
  /**
   * saveSector
   */
  public saveSector() {
    this.sectorsService.save(this.sector).subscribe(() => {
      swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
      this._router.navigate(['sectors', 'list']);
    });
  }
}