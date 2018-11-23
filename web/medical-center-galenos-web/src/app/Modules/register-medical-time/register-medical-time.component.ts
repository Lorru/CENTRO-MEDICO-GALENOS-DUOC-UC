import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
      selector: 'app-register-medical-time',
      templateUrl: './register-medical-time.component.html',
      styleUrls: ['./register-medical-time.component.css']
})
export class RegisterMedicalTimeComponent implements OnInit {

      profileRoles: Array<any> = new Array<any>();
      roleId: number = 4;

      constructor(private router: Router) { }

      ngOnInit() {
            this.profileRoles = JSON.parse(localStorage.getItem('profileRole'));
            this.validateProfile();
      }

      validateProfile(){

            this.profileRoles = this.profileRoles.filter(res => res.roleId == this.roleId);

            if(this.profileRoles.length == 0){

                  this.router.navigate(['/menu/home']);

            }

      }

}
