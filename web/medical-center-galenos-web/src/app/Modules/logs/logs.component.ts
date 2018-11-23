import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.css']
})
export class LogsComponent implements OnInit {

  profileRoles: Array<any> = new Array<any>();
  roleId: number = 13;

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
