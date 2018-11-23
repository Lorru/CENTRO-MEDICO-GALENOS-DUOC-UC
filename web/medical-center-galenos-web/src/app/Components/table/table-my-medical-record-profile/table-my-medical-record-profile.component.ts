import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../Services/user.service';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { ModalEnableDisableMyProfileComponent } from '../../modal/modal-enable-disable-my-profile/modal-enable-disable-my-profile.component';
import { ModalEditPatientComponent } from '../../modal/modal-edit-patient/modal-edit-patient.component';
import { ModalEditSpecialistComponent } from '../../modal/modal-edit-specialist/modal-edit-specialist.component';
import { ModalEditPersonalComponent } from '../../modal/modal-edit-personal/modal-edit-personal.component';

@Component({
    selector: 'app-table-my-medical-record-profile',
    templateUrl: './table-my-medical-record-profile.component.html',
    styleUrls: ['./table-my-medical-record-profile.component.css'],
    providers:[
        UserService
    ]
})
export class TableMyMedicalRecordProfileComponent implements OnInit {

    userId: number;
    user: any = {};
    profileId: number;
    dateNow = new Date();
    fullYear: string;
    objectUser: any = {};

    loadingUser: boolean;


    constructor(private _userService: UserService, private _modalService: NgbModal, private router: Router, private datePipe: DatePipe) { }

    ngOnInit() {

        this.userId = JSON.parse(localStorage.getItem('userId'));
        this.profileId = JSON.parse(localStorage.getItem('profileId'));
        this.getDateNow();
        this.userFindByUserIdAndStatusActive();

    }

    userFindByUserIdAndStatusActive(){

        this.loadingUser = true;
        this._userService.findByUserIdAndStatusActive(this.userId).subscribe(res => {
            if(res.statusCode == 200){

                this.returnRun(res.userExits);
                this.returBirthDate(res.userExits);
                this.returnEmail(res.userExits);
                this.returnOld(res.userExits);
                this.returnGender(res.userExits);
                this.returnFullName(res.userExits);
                this.returnStatus(res.userExits);
                this.returnObjectUser(res.userExits);
                this.returnBranchOffice(res.userExits);
                this.returnCategory(res.userExits);
                this.returnSpecialty(res.userExits);
                this.returnObjectUserUpdate(res.userExits);

                this.loadingUser = false;

            }else if(res.statusCode == 204){

                this.loadingUser = false;

            }else if(res.statusCode == 401){

                localStorage.clear();
                this.router.navigate(['/']);

            }else if(res.statusCode == 500){

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;

          }

        })


    }

    returnRun(objectUser: any){

        if(this.profileId == 5){

            this.user.run = objectUser.patientId.patientRun;

        }else if(this.profileId == 2){

            this.user.run = objectUser.specialistId.specialistRun;

        }else{

            this.user.run = objectUser.personalId.personalRun;

        }

    }

    returnBranchOffice(objectUser: any){

        if(this.profileId == 5){

            this.user.branchOffice = "No pertenece a ningun centro médico";

        }else if(this.profileId == 2){

            this.user.branchOffice = objectUser.specialistId.branchOfficeId.branchOfficeName;

        }else{

            this.user.branchOffice = objectUser.personalId.branchOfficeId.branchOfficeName;

        }

    }

    returnCategory(objectUser: any){

        if(this.profileId == 5){

            this.user.category = "No tiene categoria";

        }else if(this.profileId == 2){

            this.user.category = "No tiene categoria";

        }else{

            this.user.category = objectUser.personalId.categoryId.categoryDescription;

        }

    }

    returnSpecialty(objectUser: any){

        if(this.profileId == 5){

            this.user.specialty = "No tiene especialidad";

        }else if(this.profileId == 2){

            this.user.specialty = objectUser.specialistId.specialtyId.specialtyDescription;

        }else{

            this.user.specialty = "No tiene especialidad";

        }

    }

    returnFullName(objectUser: any){
        
        if(this.profileId == 5){

            this.user.fullName = objectUser.patientId.patientFullName;
            localStorage.setItem('userName', this.user.fullName);

        }else if(this.profileId == 2){

            this.user.fullName = objectUser.specialistId.specialistFullName;
            localStorage.setItem('userName', this.user.fullName);

        }else{

            this.user.fullName = objectUser.personalId.personalFullName;
            localStorage.setItem('userName', this.user.fullName);

        }
    }

    returBirthDate(objectUser: any){
        
        if(this.profileId == 5){

            this.user.birthDate = this.transformBillMedicalTime(objectUser.patientId.patientBirthDate);

        }else if(this.profileId == 2){

            this.user.birthDate = this.transformBillMedicalTime(objectUser.specialistId.specialistBirthDate);

        }else{

            this.user.birthDate = this.transformBillMedicalTime(objectUser.personalId.personalBirthDate);

        }

    }

    returnGender(objectUser: any){
        
        if(this.profileId == 5){

            this.user.gender = objectUser.patientId.genderId.genderDescription;

        }else if(this.profileId == 2){

            this.user.gender = objectUser.specialistId.genderId.genderDescription;

        }else{

            this.user.gender = objectUser.personalId.genderId.genderDescription;

        }

    }

    returnEmail(objectUser: any){
        
        if(this.profileId == 5){

            this.user.email = objectUser.patientId.patientEmail;

        }else if(this.profileId == 2){

            this.user.email = objectUser.specialistId.specialistEmail;

        }else{

            this.user.email = objectUser.personalId.personalEmail;

        }

    }

    
    returnStatus(objectUser: any){
        
        if(this.profileId == 5){

            this.user.status = objectUser.patientId.patientStatus;

        }else if(this.profileId == 2){

            this.user.status = objectUser.specialistId.specialistStatus;

        }else{

            this.user.status = objectUser.personalId.personalStatus;

        }

    }

    returnObjectUser(objectUser: any){
        
        if(this.profileId == 5){

            this.user.objectUser = objectUser.patientId;

        }else if(this.profileId == 2){

            this.user.objectUser = objectUser.specialistId;

        }else{

            this.user.objectUser = objectUser.personalId;

        }

    }

    returnOld(objectUser: any){
        
        if(this.profileId == 5){

            this.user.birthDate = this.transformBillMedicalTime(objectUser.patientId.patientBirthDate);
            let year: any = this.user.birthDate.split('-');
            year = year[0];

            let yearNow: any = this.fullYear.split('-');
            yearNow = yearNow[0];

            this.user.old = parseInt(yearNow) - parseInt(year);

        }else if(this.profileId == 2){

            this.user.birthDate = this.transformBillMedicalTime(objectUser.specialistId.specialistBirthDate);
            let year: any = this.user.birthDate.split('-');
            year = year[0];

            let yearNow: any = this.fullYear.split('-');
            yearNow = yearNow[0];

            this.user.old = parseInt(yearNow) - parseInt(year);

        }else{

            this.user.birthDate = this.transformBillMedicalTime(objectUser.personalId.personalBirthDate);
            let year: any = this.user.birthDate.split('-');
            year = year[0];

            let yearNow: any = this.fullYear.split('-');
            yearNow = yearNow[0];

            this.user.old = parseInt(yearNow) - parseInt(year);

        }

    }

    transformBillMedicalTime(billMedicalTime: string){
        return this.datePipe.transform(billMedicalTime, 'yyyy-MM-dd');
    }

    getDateNow(){

        let year: string = this.dateNow.getFullYear().toString();
        let month: string = (this.dateNow.getMonth() + 1).toString();
        let day: string = this.dateNow.getDate().toString();

        month = month.length == 1 ? '0' + month : month;
        day = day.length == 1 ? '0' + day : day;

        this.fullYear = year + '-' + month + '-' + day;
    }

    enableDisableSpecialist(){

        const modalRef = this._modalService.open(ModalEnableDisableMyProfileComponent);
        modalRef.componentInstance.objectSpecialist = this.user.objectUser;
        modalRef.result.then((result) => {
            this.getDateNow();
            this.userFindByUserIdAndStatusActive();
        }).catch((error) => {
            this.getDateNow();
            this.userFindByUserIdAndStatusActive();
        });

    }

    returnObjectUserUpdate(objectUser: any){

        if(this.profileId == 5){

            this.objectUser = objectUser;

        }else if(this.profileId == 2){

            this.objectUser = objectUser;

        }else{

            this.objectUser = objectUser;

        }
   
    }

    updateInformationPersonal(){


        if(this.profileId == 5){

            const modalRef = this._modalService.open(ModalEditPatientComponent);
            modalRef.componentInstance.objectPatient = this.objectUser;
            modalRef.result.then((result) => {
                this.getDateNow();
                this.userFindByUserIdAndStatusActive();
            }).catch((error) => {
                this.getDateNow();
                this.userFindByUserIdAndStatusActive();
            });

        }else if(this.profileId == 2){

            const modalRef = this._modalService.open(ModalEditSpecialistComponent);
            modalRef.componentInstance.objectSpecialist = this.objectUser;
            modalRef.result.then((result) => {
                this.getDateNow();
                this.userFindByUserIdAndStatusActive();
            }).catch((error) => {
                this.getDateNow();
                this.userFindByUserIdAndStatusActive();
            });

        }else{

            const modalRef = this._modalService.open(ModalEditPersonalComponent);
            modalRef.componentInstance.objectPersonal = this.objectUser;
            modalRef.result.then((result) => {
                this.getDateNow();
                this.userFindByUserIdAndStatusActive();
            }).catch((error) => {
                this.getDateNow();
                this.userFindByUserIdAndStatusActive();
            });

        }

    }

}
