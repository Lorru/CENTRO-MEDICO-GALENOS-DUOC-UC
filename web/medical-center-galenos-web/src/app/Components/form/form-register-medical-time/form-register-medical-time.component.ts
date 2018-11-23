import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { BranchOfficeService } from '../../../Services/branchOffice.service';
import { PatientService } from '../../../Services/patient.service';
import { ScheduleService } from '../../../Services/schedule.service';
import { SpecialistService } from '../../../Services/specialist.service';
import { SpecialtyService } from '../../../Services/specialty.service';
import { ModalConfirmationMedicalTimeComponent } from '../../modal/modal-confirmation-medical-time/modal-confirmation-medical-time.component';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';


@Component({
      selector: 'app-form-register-medical-time',
      templateUrl: './form-register-medical-time.component.html',
      styleUrls: ['./form-register-medical-time.component.css'],
      providers: [
            BranchOfficeService,
            PatientService,
            ScheduleService,
            SpecialistService,
            SpecialtyService
      ]
})
export class FormRegisterMedicalTimeComponent implements OnInit {

      model: any = {};

      dateNow = new Date();
      dateMinMedicalTime: string;
      compareLocalTimeSchedule: string;
      profileId: number = 5;

      loadingBranchOffice: boolean;
      loadingPatient: boolean;
      loadingSchedule: boolean;
      loadingSpecialist: boolean;
      loadingSpecialty: boolean;

      branchOffices: Array<any> = new Array<any>();
      patients: Array<any> = new Array<any>();
      schedules: Array<any> = new Array<any>();
      specialists: Array<any> = new Array<any>();
      specialtys: Array<any> = new Array<any>();

      branchOfficeName: string;
      specialtyName: string;
      specialistName: string;
      scheduleHour: string;

      constructor(private _branchOfficeService: BranchOfficeService, private _patientService: PatientService, private _scheduleService: ScheduleService, private _specialistService: SpecialistService, private _specialtyService: SpecialtyService, private _modalService: NgbModal, private router: Router) { }

      ngOnInit() {

            this.profileId = JSON.parse(localStorage.getItem('profileId'));

            this.getMinMedicalTime();
            this.branchOfficeFindAll();
            this.patientFindAll();
      }

      validateRegisterMedicalTime(){

            if(this.profileId == 5 && this.specialists.length > 0){

                  return true;

            }else if(this.profileId != 5 && this.patients.length > 0 && this.specialists.length > 0){

                  return true;

            }else{

                  return false;

            }

      }

      compareLocalTimeScheduleHour(){


            let hour: string = this.dateNow.getHours().toString();
            let minute: string = this.dateNow.getMinutes().toString();

            hour = hour.length == 1 ? 0 + hour : hour;
            minute = minute.length == 1 ? 0 + minute : minute;

            this.compareLocalTimeSchedule = hour + ':' + minute;

            if(this.model.billMedicalTime == this.dateMinMedicalTime){

                  this.schedules = this.schedules.filter(res => res.scheduleHour > this.compareLocalTimeSchedule);

                  if(this.schedules.length > 0){

                        this.model.scheduleId = this.schedules[0].scheduleId;

                  }
            }

      }

      confirmationMedicalTime(){

            let objectBranchOffice: any = this.branchOffices.filter(res => res.branchOfficeId == this.model.branchOfficeId);

            let objectSpecialty: any = this.specialtys.filter(res => res.specialtyId == this.model.specialtyId);

            let objectSpecialist: any = this.specialists.filter(res => res.specialistId == this.model.specialistId);

            let objectSchedule: any = this.schedules.filter(res => res.scheduleId == this.model.scheduleId);

            let objectPatient: any = this.patients.filter(res => res.patientId == this.model.patientId);

            const modalRef = this._modalService.open(ModalConfirmationMedicalTimeComponent);
            modalRef.componentInstance.objectBranchOffice = objectBranchOffice 
            modalRef.componentInstance.objectSpecialty = objectSpecialty
            modalRef.componentInstance.objectSpecialist = objectSpecialist
            modalRef.componentInstance.objectSchedule = objectSchedule
            modalRef.componentInstance.objectPatient = objectPatient
            modalRef.componentInstance.billMedicalTime = this.model.billMedicalTime;
            modalRef.componentInstance.formRegisterMedicalTimeComponent = this;
            
      }

      getMinMedicalTime(){

            let year: string = this.dateNow.getFullYear().toString();
            let month: string = (this.dateNow.getMonth() + 1).toString();
            let day: string = this.dateNow.getDate().toString();

            month = month.length == 1 ? '0' + month : month;
            day = day.length == 1 ? '0' + day : day;

            this.dateMinMedicalTime = year + '-' + month + '-' + day;

            this.model.billMedicalTime = this.dateMinMedicalTime;     
      }

      branchOfficeFindAll(){

            this.loadingBranchOffice = true;
            this._branchOfficeService.findAll().subscribe(res => {

                  if(res.statusCode == 200){

                        this.branchOffices = res.branchOffice;
                        this.model.branchOfficeId = res.branchOffice[0].branchOfficeId;
                        this.loadingBranchOffice = false;
                        this.specialtyFindByBranchOfficeId();

                  }else if(res.statusCode == 204){

                        this.branchOffices.length = 0;
                        this.loadingBranchOffice = false;

                  }else if(res.statusCode == 500){

                        this.loadingBranchOffice = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);
                  }
            })
      }

      patientFindAll(){

            this.loadingPatient = true;
            this._patientService.findAll().subscribe(res => {

                  if(res.statusCode == 200){

                        this.patients = res.patient;
                        this.model.patientId = res.patient[0].patientId;
                        this.loadingPatient = false;

                  }else if(res.statusCode == 204){

                        this.patients.length = 0;
                        this.loadingPatient = false;

                  }else if(res.statusCode == 500){

                        this.loadingPatient = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);
                  }

            })
      }

      specialtyFindByBranchOfficeId(){

            this.loadingSpecialty = true;
            this._specialtyService.findByBranchOfficeId(this.model.branchOfficeId).subscribe(res => { 

                  if(res.statusCode == 200){

                        this.specialtys = res.specialty;
                        this.model.specialtyId = res.specialty[0].specialtyId;
                        this.loadingSpecialty = false;
                        this.specialistFindAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive();

                  }else if(res.statusCode == 204){

                        this.specialtys.length = 0;
                        this.loadingSpecialty = false;

                  }else if(res.statusCode == 500){

                        this.loadingSpecialty = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);
                  }
            })
      }

      specialistFindAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive(){

            this.loadingSpecialist = true;
            this._specialistService.findAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive(this.model.specialtyId, this.model.branchOfficeId).subscribe(res => {
                  if(res.statusCode == 200){

                        this.specialists = res.specialist;
                        this.model.specialistId = res.specialist[0].specialistId;
                        this.loadingSpecialist = false;
                        this.scheduleFindAllBySpecialistIdAndByBillMedicalTime();

                  }else if(res.statusCode == 204){

                        this.specialists.length = 0;
                        this.schedules.length = 0;
                        this.loadingSpecialist = false;

                  }else if(res.statusCode == 500){

                        this.loadingSpecialist = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);
                  }
            })
      }


      scheduleFindAllBySpecialistIdAndByBillMedicalTime(){
            
            this.loadingSchedule = true;
            this._scheduleService.findAllBySpecialistIdAndByBillMedicalTime(this.model.specialistId, this.model.billMedicalTime).subscribe(res => {
                  if(res.statusCode == 200){
                        this.schedules = res.schedule;
                        this.model.scheduleId = res.schedule[0].scheduleId;
                        this.compareLocalTimeScheduleHour();
                        this.loadingSchedule = false;

                  }else if(res.statusCode == 204){

                        this.schedules.length = 0;
                        this.loadingSchedule = false;

                  }else if(res.statusCode == 500){

                        this.loadingSchedule = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);
                  }
            })
      }
}
