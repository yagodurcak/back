import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { TypeaheadMatch } from 'ngx-bootstrap/typeahead';
import { JobsService } from '../../../services/jobs.service';
import { Job } from '../../../interfaces/job';

@Component({
  selector: 'app-select-job',
  templateUrl: './select-job.component.html',
  styleUrls: ['./select-job.component.scss']
})
export class SelectJobComponent implements OnInit {

	public jobs:[];

	@Output() selectedComponent = new EventEmitter<Job>();


	public job:Job = {id:null, number:"", item:"", description:"", kind:null, components:[], pictures:[], planes:[], documents:[], searchField:null};

	constructor(private jobsService:JobsService) {
		this.jobsService.search("").subscribe((response:[]) => {
						this.jobs = response;
						this.jobs.forEach((job:any) => job.searchField = job.number + "-" + job.item + " - " + job.description)
        }); 
	}

	ngOnInit() {}

	public typeaheadOnSelect(e: TypeaheadMatch): void {
		this.selectedComponent.emit(e.item);
		
    }
}
