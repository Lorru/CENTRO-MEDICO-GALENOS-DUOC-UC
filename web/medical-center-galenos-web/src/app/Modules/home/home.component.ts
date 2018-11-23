import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
      selector: 'app-home',
      templateUrl: './home.component.html',
      styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

      userName: string;
      profileRoles: Array<any> = new Array<any>();
      roleId: number = 3;
      profileId: number;

      constructor(private router: Router) { }

      ngOnInit() {
            this.userName = localStorage.getItem('userName');
            this.profileRoles = JSON.parse(localStorage.getItem('profileRole'));
            this.profileId = JSON.parse(localStorage.getItem('profileId'));
            this.validateProfile();
      }

      validateProfile(){

            this.profileRoles = this.profileRoles.filter(res => res.roleId == this.roleId);

            if(this.profileRoles.length == 0){

                  this.router.navigate(['/']);

            }

      }

}
