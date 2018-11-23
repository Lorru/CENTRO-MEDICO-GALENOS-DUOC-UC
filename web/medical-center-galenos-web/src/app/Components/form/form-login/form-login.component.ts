import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from '../../../Services/user.service';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';

@Component({
      selector: 'app-form-login',
      templateUrl: './form-login.component.html',
      styleUrls: ['./form-login.component.css'],
      providers: [
            UserService
      ]
})
export class FormLoginComponent implements OnInit {

      model: any = {};
      loadingLogin: boolean;

      constructor(private _userService: UserService, private router: Router, private _modalService: NgbModal) { }

      ngOnInit() {
            
      }

      login(){

            this.loadingLogin = true;
            this._userService.login(this.model.patientSpecialistEmail, this.model.userPassword).subscribe(res => {
                  if(res.statusCode == 200){
                        
                        this.loadingLogin = false;
                        if(res.userConnectSuccess.profileId.profileId == 5){

                              localStorage.setItem('userName', res.userConnectSuccess.patientId.patientFullName);
                              localStorage.setItem('patientRun', res.userConnectSuccess.patientId.patientRun);
                              localStorage.setItem('patientId', res.userConnectSuccess.patientId.patientId);
                              localStorage.setItem('profileId', res.userConnectSuccess.profileId.profileId);
                              localStorage.setItem('userId', res.userConnectSuccess.userId);
                              localStorage.setItem('profileRole', JSON.stringify(res.profileRole));
                              localStorage.setItem('token', res.token);

                        }else if(res.userConnectSuccess.profileId.profileId == 2){

                              localStorage.setItem('userName', res.userConnectSuccess.specialistId.specialistFullName);
                              localStorage.setItem('specialistId', res.userConnectSuccess.specialistId.specialistId);
                              localStorage.setItem('profileId', res.userConnectSuccess.profileId.profileId);
                              localStorage.setItem('userId', res.userConnectSuccess.userId);
                              localStorage.setItem('profileRole', JSON.stringify(res.profileRole));
                              localStorage.setItem('token', res.token);

                        }else{
                              localStorage.setItem('userName', res.userConnectSuccess.personalId.personalFullName);
                              localStorage.setItem('profileId', res.userConnectSuccess.profileId.profileId);
                              localStorage.setItem('userId', res.userConnectSuccess.userId);
                              localStorage.setItem('profileRole', JSON.stringify(res.profileRole));
                              localStorage.setItem('token', res.token);
                        }

                        this.router.navigate(['/menu/home']);

                  }else if(res.statusCode == 401){

                        this.loadingLogin = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 204){

                        this.loadingLogin = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 500){

                        this.loadingLogin = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }
            })
      }
}
