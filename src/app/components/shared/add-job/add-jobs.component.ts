import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { JobsService } from '../../../services/jobs.service';
import { KindsService } from '../../../services/kinds.service';
import { Job } from '../../../interfaces/job';
import { Kind } from '../../../interfaces/kind';
import { Page } from '../../../interfaces/page';
const swal = require('sweetalert');

@Component({
  selector: 'app-add-jobs',
  templateUrl: './add-jobs.component.html'
})
export class AddJobsComponent implements OnInit {

	@Output() savedJob = new EventEmitter<Job>();
  	public job:Job = {id:null, number:"", item:"", description:"", kind:null, components:[], pictures:[], planes:[], documents:[], searchField:null};
  	public kinds:Array<Kind> = [];
  
  	constructor(private jobsService:JobsService, private kindsService:KindsService) {
    	this.kindsService.findAll(10000,1,"name","ASC","jobs").subscribe((response:Page<Kind>) => {
        	this.kinds = response.content;
    	});
	}

  ngOnInit() {
  }  
  /**
   * saveJob
   */
  	public addJob() {
    	this.jobsService.save(this.job).subscribe(
			(response:Job) => {
			    swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
			    this.savedJob.emit(response);
			},
			(response:any) => {
				if(response.error.message == 'Duplicate item or number field.')
				swal('Datos de entrada incorrectos!', 'El número del trabajo o de item ya existen para otro.', 'error');
			}
		);
  	} 
}