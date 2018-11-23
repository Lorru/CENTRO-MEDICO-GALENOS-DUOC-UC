import { Component, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalSuccessComponent } from '../modal-success/modal-success.component';
import { ModalErrorComponent } from '../modal-error/modal-error.component';
import { Router } from '@angular/router';
import { UserService } from '../../../Services/user.service';

@Component({
    selector: 'app-modal-enable-disable-user',
    templateUrl: './modal-enable-disable-user.component.html',
    styleUrls: ['./modal-enable-disable-user.component.css'],
    providers:[
        UserService
    ]
})
export class ModalEnableDisableUserComponent implements OnInit {

    userStatus: number;
    title: string;
    titleButton: string;
    message: string;
    loadingUserStatus: boolean;
    objectUser: any;
    userFullName: string;

    constructor(public activeModal: NgbActiveModal, private _userService: UserService, private _modalService: NgbModal, private router: Router) { }

    ngOnInit() {
        this.returnUserFullName();  
        this.getMessages();

    }

    returnUserFullName(){

        if(this.objectUser[0].profileId.profileId == 2){

            this.userFullName = this.objectUser[0].specialistId.specialistFullName;

        }else if(this.objectUser[0].profileId.profileId == 5){

            this.userFullName = this.objectUser[0].patientId.patientFullName;

        }else{

            this.userFullName = this.objectUser[0].personalId.personalFullName;
        }

    }

    getMessages(){

        this.userStatus = this.objectUser[0].userStatus;
        if(this.userStatus == 1){
          
            this.title = "Deshabilitar Usuario.";
            this.titleButton = "Deshabilitar";
            this.message = "¿Seguro que quiere deshabilitar al usuario " + this.userFullName + ' ?.';

        }else{

            this.title = "Habilitar Usuario.";
            this.titleButton = "Habilitar";
            this.message = "¿Seguro que quiere habilitar al usuario " + this.userFullName + ' ?.';

        }

    }

    dismiss() {
        this.activeModal.dismiss('Modal Dismiss');
    }

    enableDisableUser(){
        
        this.loadingUserStatus = true;
        this.userStatus = this.userStatus == 1 ? 0 : 1;
        this._userService.updateUserByUserIdAndUserStatus(this.objectUser[0].userId, this.userStatus.toString()).subscribe(res => {

            if(res.statusCode == 200){

                this.loadingUserStatus = false;

                const modalRef = this._modalService.open(ModalSuccessComponent);
                modalRef.componentInstance.message = this.userStatus == 1 ? 'Se habilito el usuario ' + this.userFullName + ' correctamente.' : 'Se deshabilito el usuario ' + this.userFullName + ' correctamente.';
                modalRef.componentInstance.title = this.userStatus == 1 ? 'Habilitar usuario.' : 'Deshabilitar usuario.';
                modalRef.result.then((result) => {
                    this.dismiss();
                }).catch((error) => {
                    this.dismiss();
                });

            }else if(res.statusCode == 204){

                this.loadingUserStatus = false;

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;

            }else if(res.statusCode == 500){

                this.loadingUserStatus = false;

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;

            }else if(res.statusCode == 401){

                this.dismiss();
                localStorage.clear();
                this.router.navigate(['/']);
          }

        })
    }
}
