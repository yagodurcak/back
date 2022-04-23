import { Component, OnInit, Input, EventEmitter, ElementRef, ViewChild } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { Observable } from 'rxjs';
import { PicturesService } from '../../../services/pictures.service';
import { Job } from '../../../interfaces/job';

const swal = require('sweetalert');

@Component({
	selector: 'app-pictures-job',
	templateUrl: './pictures-job.component.html',
	styleUrls: ['./pictures-job.component.scss']
})
export class PicturesJobComponent implements OnInit {

	public uploader: FileUploader = new FileUploader({});
    @Input() job:Job;
    @ViewChild('inputUploader') inputUploader: ElementRef;
    public claims:any;

	constructor(private picturesService:PicturesService) { 
        this.claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));
    }

	ngOnInit() { }

	public onSelected(event: EventEmitter<File[]>) {
	    const file: File = event[0];
	    var reader  = new FileReader();
	    let picture:any =  {id:null, modifing:true, uploading:false, source:null, main:false};
	
	    reader.addEventListener("load", () => {
	        picture.source = reader.result;
            this.job.pictures.push(picture);
            this.inputUploader.nativeElement.value = '';
	    }, false);
	
	    reader.addEventListener("error", (event) => {
	        swal('Lo sentimos!', 'La operación no pudo ser realizada con exito.', 'error');
	    }, false);
	    
	    reader.readAsDataURL(file);

	}

	public delete(picture:any){
        if(picture.id == null){
            this.job.pictures.pop();
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
                    this.picturesService.delete(picture.id, this.job.id).subscribe((response:any) => {
                        swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                        this.job.pictures = this.job.pictures.filter((picture:any) => picture.id !== response.id);
                    })
                    /** Confirmación  **/
                } else {
                    swal('Cancelado!', 'La acción se canceló :)', 'error');
                }
            });
        }
	}
	
	public save(picture:any){
        let observable:Observable<any> = this.picturesService.save(picture, this.job.id)
        if(observable != null){
            observable.subscribe((response:any) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                picture = response;
            });
        } else {
            swal('Lo sentimos!', 'La operación no pudo ser realizada con exito.', 'error');
        }
    }

    public main(pictureId:number){
        let observable:Observable<any> = this.picturesService.main(pictureId, this.job.id)
        if(observable != null){
            observable.subscribe((response:any) => {
                swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
                this.job.pictures = response;
            });
        } else {
            swal('Lo sentimos!', 'La operación no pudo ser realizada con exito.', 'error');
        }
    }

}
