import { Component, OnInit } from '@angular/core';
import { Router , ActivatedRoute} from "@angular/router";
import { NgForm} from "@angular/forms";
import { contexts } from '../contexts';
import { KindsService } from '../../../../services/kinds.service';
import { Kind } from '../../../../interfaces/kind';
import { Context } from '../../../../interfaces/context';
const swal = require('sweetalert');

@Component({
  selector: 'app-save-kinds',
  templateUrl: './save-kinds.component.html'
})
export class SaveKindsComponent implements OnInit {

  public contexts:Array<Context> = contexts;
  public kind:Kind = {id:null, name:"", context:"", created:""};

  constructor(private _router:Router, private _activatedRoute:ActivatedRoute, private kindsService:KindsService) { }

  ngOnInit() {
    let id:number = +this._activatedRoute.snapshot.paramMap.get('id');
    if(id != 0){
      this.kindsService.find(id).subscribe((response:Kind) => {
        this.kind = response;
      });
    }
  }
  /**
   * saveKind
   */
  public saveKind(data:NgForm) {
    if(data.valid){
      this.kindsService.save(data.value).subscribe(() => {
        swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
        this._router.navigate(['kinds', 'list']);
      });
    } else{
      swal('Error en los datos!', 'Algunos datos ingresados son incorrectos.', 'error');
    }
  }
}
