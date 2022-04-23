import { Component, OnInit, Input } from '@angular/core';
import { KindsService } from '../../../services/kinds.service';
import { JobsService } from '../../../services/jobs.service';
import { Kind } from '../../../interfaces/kind';
import { Job } from '../../../interfaces/job';
import { Page } from '../../../interfaces/page';

const swal = require('sweetalert');

@Component({
  selector: 'app-header-job',
  templateUrl: './header-job.component.html',
  styleUrls: ['./header-job.component.scss']
})
export class HeaderJobComponent implements OnInit {

  @Input() job:Job;
  
  public kinds:Array<Kind> = [];
  public kindsName = [];
  public claims:any;

  constructor(public kindsService:KindsService, public jobsService:JobsService) { 
    this.claims = JSON.parse(localStorage.getItem('indumet-workload-user-claims'));
  }

  ngOnInit() {
      this.kindsService.findAll(10000,1,"name","ASC","jobs").subscribe((response:Page<Kind>) => {
        this.kinds = response.content;
        this.kindsName = this.kinds.map((kind:Kind) => {return {value:kind.name, text:kind.name}});
      });
  }

  public save(event:any, field?:string){
    if(field == 'description'){
        this.job.kind = this.kinds.filter((kind:Kind) => kind.name == this.job.kind.name)[0];
    }
    this.jobsService.save(this.job).subscribe(() => {
      swal('Operación exitosa!', 'La operación se realizó con exito.', 'success');
    });
  
  }
}