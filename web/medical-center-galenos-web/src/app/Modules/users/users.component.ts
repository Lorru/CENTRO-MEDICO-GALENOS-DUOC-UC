import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  profileRoles: Array<any> = new Array<any>();
  roleId: number = 9;

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
