import { Component, OnInit } from '@angular/core';
import { Router , ActivatedRoute} from "@angular/router";
import { NgForm} from "@angular/forms";
import { WorkersService } from '../../../../services/workers.service';
import { Worker } from '../../../../interfaces/worker';
const swal = require('sweetalert');

@Component({
  selector: 'app-save-workers',
  templateUrl: './save-workers.component.html'
})
export class SaveWorkersComponent implements OnInit {

  public worker:Worker = {id:null, name:"", created:""};

  constructor(private _router:Router, private _activatedRoute:ActivatedRoute, private workersService:WorkersService) { }

  ngOnInit() {
    let id:number = +this._activatedRoute.snapshot.paramMap.get('id');
    if(id != 0){
      this.workersService.find(id).subscribe((response:Worker) => {
        this.worker = response;
      });
    }
  }
  /**
   * saveWorker
   */
  public saveWorker(data:NgForm) {
    if(data.valid){
      this.workersService.save(data.value).subscribe(() => {
        swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
        this._router.navigate(['workers', 'list']);
      });
    } else{
      swal('Error en los datos!', 'Algunos datos ingresados son incorrectos.', 'error');
    }
  }

}
