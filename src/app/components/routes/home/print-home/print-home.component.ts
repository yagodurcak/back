import {Component, Input, OnInit} from '@angular/core';
import { ColorsService } from '../../../shared/colors/colors.service';
import * as moment from 'moment';
import { JobOrder } from '../../../../interfaces/job-order';

@Component({
    selector: 'app-print-home',
    templateUrl: './print-home.component.html',
    styleUrls: ['./print-home.component.scss']
})
export class PrintHomeComponent implements OnInit {

  @Input() jobOrders: Array<JobOrder>;
  sparkOptionPie = {
        type: 'pie',
        width: '2em',
        height: '2em',
        sliceColors: [this.colors.byName('success'), this.colors.byName('gray-light')]
    };

  constructor(private colors: ColorsService) {
  }

  ngOnInit() {
  }

  public isBackward(date:string){
    return moment(date, "DD/MM/YYYY").isBefore(moment(new Date()));
  }

  public print(){
    //document.getElementsByClassName("aside-container")[0].style.display = 'none';
    window.print();
    let tempTitle = document.title;
    let d = new Date();
    document.title = "Planilla principal - " + d.getDate() + "_" + d.getMonth() + "_" + d.getFullYear();
    setTimeout(() => {
        document.title = tempTitle;
        // document.getElementsByClassName("aside-container")[0].style.display = '';
      }, 1000
    );

  }
}
