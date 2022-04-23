import { Component, OnInit, Input, EventEmitter, ElementRef, ViewChild } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { DocumentsService } from '../../../services/documents.service';
import { Observable } from 'rxjs';
import { KindsService } from '../../../services/kinds.service';
import { JobOrder } from '../../../interfaces/job-order';

const swal = require('sweetalert');


@Component({
  selector: 'app-documents-job-order',
  templateUrl: './documents-job-order.component.html',
  styleUrls: ['./documents-job-order.component.scss']
})
export class DocumentsJobOrderComponent implements OnInit {

  public uploader: FileUploader = new FileUploader({});

	@Input() jobOrder:JobOrder;
	public kindDocuments:[];
	@ViewChild('inputUploader') inputUploader: ElementRef;

	public claims:any;

  constructor(private documentsService:DocumentsService, private kindsService:KindsService) { 
		this.claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));

	}

  ngOnInit() {
		this.kindsService.findAll(10000,1,"name","ASC","job_order_documents").subscribe((response:any) => {
			this.kindDocuments = response.content;
		});
  }

	public save(document:any){
	    let observable:Observable<any> = this.documentsService.save("jobOrders", document, this.jobOrder.id);
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
					this.jobOrder.documents.pop();
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
									this.documentsService.delete("jobOrders", document.id, this.jobOrder.id).subscribe((response:any) => {
											swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
											this.jobOrder.documents = this.jobOrder.documents.filter((document:any) => document.id !== response.id);
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
						this.jobOrder.documents.push(document);
						this.inputUploader.nativeElement.value = '';
      	}, false);
  
      	reader.addEventListener("error", (event) => {
          	swal('lo sentimos!', 'La operación no pudo ser realizada con exito.', 'error');
      	}, false);
      
      	reader.readAsDataURL(file);
  
  }

}
