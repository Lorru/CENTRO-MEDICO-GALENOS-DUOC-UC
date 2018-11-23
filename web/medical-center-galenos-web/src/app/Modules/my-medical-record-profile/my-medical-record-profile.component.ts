import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-my-medical-record-profile',
    templateUrl: './my-medical-record-profile.component.html',
    styleUrls: ['./my-medical-record-profile.component.css']
})
export class MyMedicalRecordProfileComponent implements OnInit {

    profileId: number;

    profileRoles: Array<any> = new Array<any>();
    roleId: number = 1;

    constructor(private router: Router) { }

    ngOnInit() {

        this.profileId = JSON.parse(localStorage.getItem('profileId'));
        this.profileRoles = JSON.parse(localStorage.getItem('profileRole'));
        this.validateProfile();

    }

    titleProfile(){

        if(this.profileId == 5){

              return 'Mi Ficha Médica';

        }else{

              return 'Mi Perfil';
        }

    }

    validateProfile(){

          this.profileRoles = this.profileRoles.filter(res => res.roleId == this.roleId);

          if(this.profileRoles.length == 0){

            this.router.navigate(['/menu/home']);

          }

    }

}
