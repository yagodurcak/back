import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute} from "@angular/router";
import { Provider } from '../../../../interfaces/provider';
import { ProvidersService } from '../../../../services/providers.service';
const swal = require('sweetalert');

@Component({
  selector: 'app-save-providers',
  templateUrl: './save-providers.component.html',
  styleUrls: ['./save-providers.component.scss']
})
export class SaveProvidersComponent implements OnInit {

  public provider:Provider = {id: null,name: "",description: "",address: "",mail: "",contact: "",cuit: "",phone: ""};

  constructor(private _router:Router, private providersService:ProvidersService, private _activatedRoute:ActivatedRoute) {}
  ngOnInit() {
    let id:number = +this._activatedRoute.snapshot.paramMap.get('id');
    if(id != 0){
      this.providersService.find(id).subscribe((response:Provider) => {
        this.provider = response;
      });
    }
  }
    
  /**
   * saveProvider
   */
  public saveProvider() {
    this.providersService.save(this.provider).subscribe(() => {
      swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
      this._router.navigate(['providers', 'list']);
    });
  }
}