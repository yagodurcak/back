import { Component, OnInit, Input } from '@angular/core';
import { Job } from '../../../interfaces/job';

@Component({
  selector: 'app-card-job',
  templateUrl: './card-job.component.html',
  styleUrls: ['./card-job.component.scss']
})
export class CardJobComponent implements OnInit {

  @Input() job: Job;

  constructor() { }

  ngOnInit() {
  }

}
