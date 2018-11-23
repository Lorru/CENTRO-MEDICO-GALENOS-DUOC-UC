import { Component, OnInit } from '@angular/core';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { UserService } from '../../../Services/user.service';
import { ModalEnableDisableUserComponent } from '../../modal/modal-enable-disable-user/modal-enable-disable-user.component';
import { ModalEditPatientComponent } from '../../modal/modal-edit-patient/modal-edit-patient.component';
import { ModalEditSpecialistComponent } from '../../modal/modal-edit-specialist/modal-edit-specialist.component';
import { ModalEditPersonalComponent } from '../../modal/modal-edit-personal/modal-edit-personal.component';

@Component({
    selector: 'app-table-user',
    templateUrl: './table-user.component.html',
    styleUrls: ['./table-user.component.css'],
    providers: [
        UserService
    ]
})
export class TableUserComponent implements OnInit {

    currentPage: number = 1;
    countRowsByPage: number = 5;
    countRows: number;
    loadingUsers: boolean;
    objectUser: any = {};

    users: Array<any> = new Array<any>();

    constructor(private _userService: UserService, private _modalService: NgbModal, private router: Router) { }

    ngOnInit() {

        this.userFindAll();

    }

    returnUserEmail(user: any){

        if(user.profileId.profileId == 2){

            return user.specialistId.specialistEmail;

        }else if(user.profileId.profileId == 5){

            return user.patientId.patientEmail;

        }else{

            return user.personalId.personalEmail;

        }

    }

    returnUserFullName(user: any){

        if(user.profileId.profileId == 2){

            return user.specialistId.specialistFullName;

        }else if(user.profileId.profileId == 5){

            return user.patientId.patientFullName;

        }else{

            return user.personalId.personalFullName;

        }

    }

    returnUserStatus(UserStatus: number){

        if(UserStatus == 1){

            return 'HABILITADO';

        }else{

            return 'INHABILITADO';

        }
    }

    userFindAll(){
        this.currentPage = this.currentPage == 0 ? 1 : this.currentPage;
        this.currentPage = this.currentPage - 1;
        this.loadingUsers = true;
        this._userService.findAll(this.currentPage).subscribe(res => {
            if(res.statusCode == 200){
              
                this.users = res.user;
                this.currentPage = res.currentPage;
                this.countRows = res.countRows;
                this.countRowsByPage = res.countRowsByPage;
                this.loadingUsers = false;

            }else if(res.statusCode == 204){

                this.users.length = 0;
                this.loadingUsers = false;


            }else if(res.statusCode == 401){

                localStorage.clear();
                this.router.navigate(['/']);

            }else if(res.statusCode == 500){

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;

            }
        })

    }

    pageChange(e){

        this.currentPage = e - 1;
        this.loadingUsers = true;
        this._userService.findAll(this.currentPage).subscribe(res => {
            if(res.statusCode == 200){

                this.users = res.user;
                this.currentPage = res.currentPage + 1;
                this.loadingUsers = false;

            }else if(res.statusCode == 204){

                this.users.length = 0;
                this.loadingUsers = false;


            }else if(res.statusCode == 401){

                localStorage.clear();
                this.router.navigate(['/']);

            }else if(res.statusCode == 500){

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;

            }
        })
    }

    enableDisableUser(userId: number){

        const modalRef = this._modalService.open(ModalEnableDisableUserComponent);
        modalRef.componentInstance.objectUser = this.users.filter(res => res.userId == userId);
        modalRef.result.then((result) => {
            this.currentPage = 1;
            this.countRowsByPage = 5;
            this.userFindAll();
        }).catch((error) => {
            this.currentPage = 1;
            this.countRowsByPage = 5;
            this.userFindAll();
        });

    }

    returnObjectUser(userId: number){

      let objectUser: any  = this.users.filter(res => res.userId == userId);

      if(objectUser[0].profileId.profileId == 2){

            let objectSpecialist: any = {
                  userId: objectUser[0].userId,
                  profileId:{
                        profileId: objectUser[0].profileId.profileId
                  },
                  specialistId: objectUser[0].specialistId
            }

            this.objectUser = objectSpecialist;

      }else if(objectUser[0].profileId.profileId == 5){

            let objectPatient: any = {
                  userId: objectUser[0].userId,
                  profileId:{
                        profileId: objectUser[0].profileId.profileId
                  },
                  patientId: objectUser[0].patientId
            }

            this.objectUser = objectPatient;

      }else{

            let objectPersonal: any = {
                  userId: objectUser[0].userId,
                  profileId:{
                        profileId: objectUser[0].profileId.profileId
                  },
                  personalId: objectUser[0].personalId
            }

            this.objectUser = objectPersonal;

      }

      this.updateInformationPersonal(this.objectUser);

    }

    updateInformationPersonal(objectUser: any){

      if(objectUser.profileId.profileId == 5){

            const modalRef = this._modalService.open(ModalEditPatientComponent);
            modalRef.componentInstance.objectPatient = objectUser;
            modalRef.result.then((result) => {
                  this.currentPage = 1;
                  this.countRowsByPage = 5;
                  this.userFindAll();
            }).catch((error) => {
                  this.currentPage = 1;
                  this.countRowsByPage = 5;
                  this.userFindAll();
            });

      }else if(objectUser.profileId.profileId == 2){

            const modalRef = this._modalService.open(ModalEditSpecialistComponent);
            modalRef.componentInstance.objectSpecialist = objectUser;
            modalRef.result.then((result) => {
                  this.currentPage = 1;
                  this.countRowsByPage = 5;
                  this.userFindAll();
            }).catch((error) => {
                  this.currentPage = 1;
                  this.countRowsByPage = 5;
                  this.userFindAll();
            });

      }else{

            const modalRef = this._modalService.open(ModalEditPersonalComponent);
            modalRef.componentInstance.objectPersonal = objectUser;
            modalRef.result.then((result) => {
                  this.currentPage = 1;
                  this.countRowsByPage = 5;
                  this.userFindAll();
            }).catch((error) => {
                  this.currentPage = 1;
                  this.countRowsByPage = 5;
                  this.userFindAll();
            });

      }

    }
}
