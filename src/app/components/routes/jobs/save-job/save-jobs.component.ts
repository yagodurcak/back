import { Component, OnInit } from '@angular/core';
import { Router} from "@angular/router";
import { Job } from '../../../../interfaces/job';
@Component({
  selector: 'app-save-jobs',
  templateUrl: './save-jobs.component.html'
})
export class SaveJobsComponent implements OnInit {
  	constructor(private _router:Router) {}
    ngOnInit() {}
    
    public toJobView(job:Job){
      this._router.navigate(['jobs', 'view', job.id]);
    }
}


