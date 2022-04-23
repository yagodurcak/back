import { Component, OnInit, Input, EventEmitter, ElementRef, ViewChild } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { PlanesService } from '../../../services/planes.service';
import { Observable } from 'rxjs';
import { KindsService } from '../../../services/kinds.service';
import { Job } from '../../../interfaces/job';

const swal = require('sweetalert');

@Component({
  	selector: 'app-planes-job',
  	templateUrl: './planes-job.component.html',
  	styleUrls: ['./planes-job.component.scss']
})
export class PlanesJobComponent implements OnInit {
	
	public uploader: FileUploader = new FileUploader({});

	@Input() job:Job;
	public kindPlanes:[];
	@ViewChild('inputUploader') inputUploader: ElementRef;
	public claims:any;

  	constructor(private planesService:PlanesService, private kindsService:KindsService) { 
		this.claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));
	}

  	ngOnInit() {
		this.kindsService.findAll(10000,1,"name","ASC","planes").subscribe((response:any) => {
            this.kindPlanes = response.content;
        });
	  }

	public save(plane:any){
	    plane.job = {id:this.job.id, number:this.job.number, item:this.job.item};
	    let observable:Observable<any> = this.planesService.save(plane, this.job.id);
	    if(observable != null){
	        observable.subscribe((response:any) => {
	            swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
	            plane.id = response.id;
				plane.source = response.source;
	        });
	    } else {
	        swal('Lo sentimos!', 'La operación no pudo ser realizada con exito.', 'error');
	    }
	}
  
  	public delete(plane:any){
     	if(plane.id == null){
          	this.job.planes.pop();
      	} else {
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
                  	this.planesService.delete(plane.id, this.job.id).subscribe((response:any) => {
                  	    swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                      	this.job.planes = this.job.planes.filter((plane:any) => plane.id !== response.id);
                  	})
                  	/** Confirmación  **/
              	} else {
               		swal('Cancelado!', 'La acción se canceló :)', 'error');
              	}
          });
      	}
  	}
  
	/** Upload */
	public onSelected(event: EventEmitter<File[]>) {
      	const file: File = event[0];
      	var reader  = new FileReader();
      	let plane =  {id:null, description:file.name, kind:null, modifing:true, uploading:false, source:null};
  
      	reader.addEventListener("load", () => {
          		plane.source = reader.result;
				  this.job.planes.push(plane);
				  this.inputUploader.nativeElement.value = '';
      	}, false);
  
      	reader.addEventListener("error", (event) => {
          	swal('lo sentimos!', 'La operación no pudo ser realizada con exito.', 'error');
      	}, false);
      
      	reader.readAsDataURL(file);
  
  }
  
}
