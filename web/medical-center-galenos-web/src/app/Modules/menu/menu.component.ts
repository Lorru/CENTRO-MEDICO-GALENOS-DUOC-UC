import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
      selector: 'app-menu',
      templateUrl: './menu.component.html',
      styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

      userName: string;
      profileId: number;
      token: string;

      profileRoles: Array<any> = new Array<any>();

      constructor(private router: Router) { }

      ngOnInit() {

            this.token = localStorage.getItem('token');
            this.userName = localStorage.getItem('userName');
            this.profileId = JSON.parse(localStorage.getItem('profileId'));
            this.profileRoles = JSON.parse(localStorage.getItem('profileRole'));
            this.validateToken();

      }

      titleProfile(){

            if(this.profileId == 5){

                  return 'Mi Ficha Médica';

            }else{

                  return 'Mi Perfil';
            }

      }

      logout(){

            localStorage.clear();
            this.router.navigate(['/']);
            
      }

      validateToken(){

            if(this.token == null || this.token == ""){

                  this.router.navigate(['/']);
            }

      }
}
