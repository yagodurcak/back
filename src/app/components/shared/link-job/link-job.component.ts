import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Job } from '../../../interfaces/job';
@Component({
  selector: 'app-link-job',
  templateUrl: './link-job.component.html',
  styleUrls: ['./link-job.component.scss']
})
export class LinkJobComponent implements OnInit {

	@Input() job:Job;

	@Output() selectedJob = new EventEmitter<Job>();

	constructor() { }

	ngOnInit() {
	}

	public jobReady(job:Job){
		this.job = job;
		this.selectedJob.emit(job);
	}
}
