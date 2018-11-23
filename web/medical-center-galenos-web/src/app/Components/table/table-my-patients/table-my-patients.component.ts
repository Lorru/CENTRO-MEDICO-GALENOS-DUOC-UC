import { Component, OnInit } from '@angular/core';
import { BillService } from '../../../Services/bill.service';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';
import { ModalAttendedPatientComponent } from '../../modal/modal-attended-patient/modal-attended-patient.component';

@Component({
    selector: 'app-table-my-patients',
    templateUrl: './table-my-patients.component.html',
    styleUrls: ['./table-my-patients.component.css'],
    providers:[
        BillService
    ]
})
export class TableMyPatientsComponent implements OnInit {

    specialistId: number;

    currentPageOnHold: number = 1;
    countRowsByPageOnHold: number = 5;
    countRowsOnHold: number;

    currentPageAttended: number = 1;
    countRowsByPageAttended: number = 5;
    countRowsAttended: number;

    loadingOnHold: boolean;
    loadingAttended: boolean;

    patientsOnHold: Array<any> = new Array<any>();
    patientsAttended: Array<any> = new Array<any>();

    constructor(private _billService: BillService, private _modalService: NgbModal, private router: Router, private datePipe: DatePipe) { }

    ngOnInit() {

        this.specialistId = JSON.parse(localStorage.getItem('specialistId'));
        this.billFindAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive();
        this.billFindAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive();
    }

    transformBillMedicalTime(billMedicalTime: string){
        return this.datePipe.transform(billMedicalTime, 'yyyy-MM-dd');
    }

    pageChangeOnHold(e){

      this.currentPageOnHold = e - 1;
      this.loadingOnHold = true;
      this._billService.findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive(this.specialistId,this.currentPageOnHold).subscribe(res => {
            if(res.statusCode == 200){
                  this.patientsOnHold = res.bill;
                  this.currentPageOnHold = res.currentPage + 1;
                  this.loadingOnHold = false;

            }else if(res.statusCode == 204){

                  this.patientsOnHold.length = 0;
                  this.loadingOnHold = false;

            }else if(res.statusCode == 401){

                  localStorage.clear();
                  this.router.navigate(['/']);

            }else if(res.statusCode == 500){

                  const modalRef = this._modalService.open(ModalErrorComponent);
                  modalRef.componentInstance.message = res.message;

            }
      })
    }

    billFindAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive(){

      this.currentPageOnHold = this.currentPageOnHold - 1;
      this.loadingOnHold = true;
      this._billService.findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive(this.specialistId, this.currentPageOnHold).subscribe(res => {
            if(res.statusCode == 200){
                  this.patientsOnHold = res.bill;
                  this.currentPageOnHold = res.currentPage;
                  this.countRowsOnHold = res.countRows;
                  this.countRowsByPageOnHold = res.countRowsByPage;
                  this.loadingOnHold = false;

            }else if(res.statusCode == 204){

                  this.patientsOnHold.length = 0;
                  this.loadingOnHold = false;

            }else if(res.statusCode == 401){

                  localStorage.clear();
                  this.router.navigate(['/']);

            }else if(res.statusCode == 500){

                  const modalRef = this._modalService.open(ModalErrorComponent);
                  modalRef.componentInstance.message = res.message;

            }
      })
    }

    pageChangeAttended(e){

      this.currentPageAttended = e - 1;
      this.loadingAttended = true;
      this._billService.findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive(this.specialistId,this.currentPageAttended).subscribe(res => {
            if(res.statusCode == 200){
                  this.patientsAttended = res.bill;
                  this.currentPageAttended = res.currentPage + 1;
                  this.loadingAttended = false;

            }else if(res.statusCode == 204){

                  this.patientsAttended.length = 0;
                  this.loadingAttended = false;

            }else if(res.statusCode == 401){

                  localStorage.clear();
                  this.router.navigate(['/']);

            }else if(res.statusCode == 500){

                  const modalRef = this._modalService.open(ModalErrorComponent);
                  modalRef.componentInstance.message = res.message;

            }
      })
    }

    billFindAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive(){

      this.currentPageAttended = this.currentPageAttended - 1;
      this.loadingAttended = true;
      this._billService.findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive(this.specialistId, this.currentPageAttended).subscribe(res => {
            if(res.statusCode == 200){
                  this.patientsAttended = res.bill;
                  this.currentPageAttended = res.currentPage;
                  this.countRowsAttended = res.countRows;
                  this.countRowsByPageAttended = res.countRowsByPage;
                  this.loadingAttended = false;

            }else if(res.statusCode == 204){

                  this.patientsAttended.length = 0;
                  this.loadingAttended = false;

            }else if(res.statusCode == 401){

                  localStorage.clear();
                  this.router.navigate(['/']);

            }else if(res.statusCode == 500){

                  const modalRef = this._modalService.open(ModalErrorComponent);
                  modalRef.componentInstance.message = res.message;

            }
      })
    }

    attendedPatient(billId: number){

      const modalRef = this._modalService.open(ModalAttendedPatientComponent);
      modalRef.componentInstance.objectPatient = this.patientsOnHold.filter(res => res.billId == billId);
      modalRef.result.then((result) => {
          this.currentPageOnHold = 1;
          this.countRowsByPageOnHold = 5;
          this.currentPageAttended = 1;
          this.countRowsByPageAttended = 5;
          this.billFindAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive();
          this.billFindAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive();
      }).catch((error) => {
            this.currentPageOnHold = 1;
            this.countRowsByPageOnHold = 5;
            this.currentPageAttended = 1;
            this.countRowsByPageAttended = 5;
            this.billFindAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive();
            this.billFindAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive();
      });

  }
}
