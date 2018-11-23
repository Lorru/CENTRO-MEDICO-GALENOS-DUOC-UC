import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
      selector: 'app-medical-hours-payment',
      templateUrl: './medical-hours-payment.component.html',
      styleUrls: ['./medical-hours-payment.component.css']
})
export class MedicalHoursPaymentComponent implements OnInit {

      profileRoles: Array<any> = new Array<any>();
      roleId: number = 10;

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
