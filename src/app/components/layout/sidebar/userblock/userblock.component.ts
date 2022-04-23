import { Component, OnInit } from '@angular/core';

import { UserblockService } from './userblock.service';

@Component({
    selector: 'app-userblock',
    templateUrl: './userblock.component.html',
    styleUrls: ['./userblock.component.scss']
})
export class UserblockComponent implements OnInit {
    user: any;
    constructor(public userblockService: UserblockService) {
        let loggedUser:any = JSON.parse(localStorage.getItem('indumet-workload-user'));
        this.user = {
            name: loggedUser.displayName,
            picture: loggedUser.photoURL,
            mail: loggedUser.email
        };
    }

    ngOnInit() {
    }

    userBlockIsVisible() {
        return this.userblockService.getVisibility();
    }

}
