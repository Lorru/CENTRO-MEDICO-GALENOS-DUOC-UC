import { Component, OnInit } from '@angular/core';
import { BillService } from '../../../Services/bill.service';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { ModalCancelMedicalTimeComponent } from '../../modal/modal-cancel-medical-time/modal-cancel-medical-time.component';

@Component({
      selector: 'app-table-my-medical-time',
      templateUrl: './table-my-medical-time.component.html',
      styleUrls: ['./table-my-medical-time.component.css'],
      providers: [
            BillService
      ]
})
export class TableMyMedicalTimeComponent implements OnInit {

      patientRun: string;
      currentPage: number = 1;
      countRowsByPage: number = 5;
      countRows: number;
      loadingBillByPatient: boolean;

      dateNow = new Date();
      fullYear: string;

      billsPatient: Array<any> = new Array<any>();

      constructor(private _billService: BillService, private _modalService: NgbModal, private router: Router, private datePipe: DatePipe) { }

      ngOnInit() {

            this.patientRun = localStorage.getItem('patientRun');
            this.billFindAllByPatientRunAndStatusActive();
            this.getDateNow();

      }

      getDateNow(){

            let year: string = this.dateNow.getFullYear().toString();
            let month: string = (this.dateNow.getMonth() + 1).toString();
            let day: string = this.dateNow.getDate().toString();

            month = month.length == 1 ? '0' + month : month;
            day = day.length == 1 ? '0' + day : day;

            this.fullYear = year + '-' + month + '-' + day;
      }

      transformBillMedicalTime(billMedicalTime: string){
            return this.datePipe.transform(billMedicalTime, 'yyyy-MM-dd');
      }

      cancelMedicalTime(billId: number){

            let objectBillPatient : any = this.billsPatient.filter(res => res.billId == billId);
            const modalRef = this._modalService.open(ModalCancelMedicalTimeComponent);
            modalRef.componentInstance.objectBillPatient = objectBillPatient;
            modalRef.result.then((result) => {
                  this.currentPage = 1;
                  this.countRowsByPage = 5;
                  this.billFindAllByPatientRunAndStatusActive();
                  this.getDateNow();
            }).catch((error) => {
                  this.currentPage = 1;
                  this.countRowsByPage = 5;
                  this.billFindAllByPatientRunAndStatusActive();
                  this.getDateNow();      
            });

      }

      pageChange(e){

            this.currentPage = e - 1;
            this.loadingBillByPatient = true;
            this._billService.findAllByPatientRunAndStatusActive(this.patientRun, this.currentPage).subscribe(res => {
                  if(res.statusCode == 200){

                        this.billsPatient = res.bill;
                        this.currentPage = res.currentPage + 1;
                        this.loadingBillByPatient = false;

                  }else if(res.statusCode == 204){

                        this.billsPatient.length = 0;
                        this.loadingBillByPatient = false;


                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);

                  }else if(res.statusCode == 500){

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }
            })
      }

      billFindAllByPatientRunAndStatusActive(){

            this.currentPage = this.currentPage - 1;
            this.loadingBillByPatient = true;
            this._billService.findAllByPatientRunAndStatusActive(this.patientRun, this.currentPage).subscribe(res => {
                  if(res.statusCode == 200){

                        this.billsPatient = res.bill;
                        this.currentPage = res.currentPage;
                        this.countRows = res.countRows;
                        this.countRowsByPage = res.countRowsByPage;
                        this.loadingBillByPatient = false;

                  }else if(res.statusCode == 204){

                        this.billsPatient.length = 0;
                        this.loadingBillByPatient = false;


                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);

                  }else if(res.statusCode == 500){

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }
            })
      }

}
