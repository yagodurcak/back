import { Component, OnInit, ViewEncapsulation, Input } from '@angular/core';
import { JobsService } from '../../../services/jobs.service';
import { Job } from '../../../interfaces/job';

const swal = require('sweetalert');

@Component({
  	selector: 'app-detail-jobs',
  	templateUrl: './detail-jobs.component.html',
  	styleUrls: ['./detail-jobs.component.scss'],
  	encapsulation: ViewEncapsulation.None
})
export class DetailJobsComponent implements OnInit {
  
	public job:Job = {id:null, number:"", item:"", description:"", kind:null, components:[], planes:[], pictures:[], documents:[], searchField:null};
    public selectedComponent:Job = {id:null, number:"", item:"", description:"", kind:null, components:[], pictures:[], planes:[], documents:[], searchField:null};
    public jobStack:Array<number> = [];
    public claims:any;

	@Input() id: number;
  
    constructor(private jobsService:JobsService) {
        this.claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));
    }

    ngOnInit() {
        this.jobsService.find(this.id).subscribe((response:Job) => {
            this.job = response;
            this.job.planes.forEach((plane) => {plane.uploaded = true;plane.uploading = false;plane.modifing = false;});
            this.job.pictures.forEach((picture) => {picture.uploaded = true;picture.uploading = false;picture.modifing = false;});
        });
    }  
    ngAfterViewInit(){ }
    ngAfterViewChecked(){ }

    public changeJob(id:number){
        this.jobStack.push(this.job.id);
        this.jobsService.find(id).subscribe((response:Job) => {
            this.job = response;
            this.job.planes.forEach((plane:any) => {plane.uploaded = true;plane.uploading = false;plane.modifing = false;});
            this.job.pictures.forEach((picture:any) => {picture.uploaded = true;picture.uploading = false;picture.modifing = false;});
          });
    }

    public goBackJob(){
        let id = this.jobStack.pop();
        this.jobsService.find(id).subscribe((response:Job) => {
            this.job = response;
            this.job.planes.forEach((plane:any) => {plane.uploaded = true;plane.uploading = false;plane.modifing = false;});
            this.job.pictures.forEach((picture:any) => {picture.uploaded = true;picture.uploading = false;picture.modifing = false;});
          });
    }

    

    public linkComponent(jobId:number, subJobId:number):void {
        this.jobsService.linkComponent(jobId, subJobId).subscribe(() => {
            swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
            this.job.components.push(this.selectedComponent);
            this.selectedComponent = {id:null, number:"", item:"", description:"", kind:null, components:[], pictures:[], planes:[], documents:[], searchField:null};
        });
    } 
}