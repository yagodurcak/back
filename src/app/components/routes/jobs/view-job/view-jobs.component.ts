import { Component, OnInit } from '@angular/core';
import { Router , ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-view-jobs',
  templateUrl: './view-jobs.component.html'
})
export class ViewJobsComponent implements OnInit {
  
    public id:number;
    constructor(private _activatedRoute:ActivatedRoute) {
        this.id = +this._activatedRoute.snapshot.paramMap.get('id');
    }

    public ngOnInit(){}
}