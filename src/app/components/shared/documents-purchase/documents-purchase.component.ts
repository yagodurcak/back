import { Component, OnInit, Input, EventEmitter, ElementRef, ViewChild } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { DocumentsService } from '../../../services/documents.service';
import { Observable } from 'rxjs';
import { KindsService } from '../../../services/kinds.service';
import { Purchase } from '../../../interfaces/purchase';

const swal = require('sweetalert');


@Component({ 
  selector: 'app-documents-purchase',
  templateUrl: './documents-purchase.component.html',
  styleUrls: ['./documents-purchase.component.scss']
})
export class DocumentsPurchaseComponent implements OnInit {

  public uploader: FileUploader = new FileUploader({});

	@Input() purchase:Purchase;
	public kindDocuments:[];
	@ViewChild('inputUploader') inputUploader: ElementRef;

  constructor(private documentsService:DocumentsService, private kindsService:KindsService) { }

  ngOnInit() {
		this.kindsService.findAll(10000,1,"name","ASC","purchase_documents").subscribe((response:any) => {
			this.kindDocuments = response.content;
		});
  }

	public save(document:any){
	    let observable:Observable<any> = this.documentsService.save("purchases", document, this.purchase.id);
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
					this.purchase.documents.pop();
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
									this.documentsService.delete("purchases", document.id, this.purchase.id).subscribe((response:any) => {
											swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
											this.purchase.documents = this.purchase.documents.filter((document:any) => document.id !== response.id);
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
						this.purchase.documents.push(document);
						this.inputUploader.nativeElement.value = '';
      	}, false);
  
      	reader.addEventListener("error", (event) => {
          	swal('lo sentimos!', 'La operación no pudo ser realizada con exito.', 'error');
      	}, false);
      
      	reader.readAsDataURL(file);
  
  }

}
