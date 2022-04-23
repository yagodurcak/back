import { Component, OnInit, Input, EventEmitter, ElementRef, ViewChild } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { DocumentsService } from '../../../services/documents.service';
import { Observable } from 'rxjs';
import { KindsService } from '../../../services/kinds.service';
import { Job } from '../../../interfaces/job';

const swal = require('sweetalert');

@Component({
  selector: 'app-documents-job',
  templateUrl: './documents-job.component.html',
  styleUrls: ['./documents-job.component.scss']
})
export class DocumentsJobComponent implements OnInit {

  
  public uploader: FileUploader = new FileUploader({});

	@Input() job:Job;
	public kindDocuments:[];
	@ViewChild('inputUploader') inputUploader: ElementRef;
	public claims:any;

  constructor(private documentsService:DocumentsService, private kindsService:KindsService) { 
		this.claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));
	}

  ngOnInit() {
		this.kindsService.findAll(10000,1,"name","ASC","job_documents").subscribe((response:any) => {
			this.kindDocuments = response.content;
		});
  }

	public save(document:any){
	    let observable:Observable<any> = this.documentsService.save("jobs", document, this.job.id);
	    if(observable != null){
	        observable.subscribe((response:any) => {
	            swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
	            document.id = response.id;
	            document.source = response.source;
	        });
	    } else {
	        swal('Lo sentimos!', 'La operación no pudo ser realizada con exito.', 'error');
	    }
	}
  
	public delete(document:any){
		if(document.id == null){
					this.job.documents.pop();
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
									this.documentsService.delete("jobs", document.id, this.job.id).subscribe((response:any) => {
											swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
											this.job.documents = this.job.documents.filter((document:any) => document.id !== response.id);
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
      	let document =  {id:null, description:file.name, kind:null, modifing:true, uploading:false, source:null};
  
      	reader.addEventListener("load", () => {
          	document.source = reader.result;
						this.job.documents.push(document);
						this.inputUploader.nativeElement.value = '';
      	}, false);
  
      	reader.addEventListener("error", (event) => {
          	swal('lo sentimos!', 'La operación no pudo ser realizada con exito.', 'error');
      	}, false);
      
      	reader.readAsDataURL(file);
  
  }
}
