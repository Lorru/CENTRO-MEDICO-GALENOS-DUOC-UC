import { Component, OnInit } from '@angular/core';
import { BillService } from '../../../Services/bill.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';
import { ModalPayMedicalTimeComponent } from '../../modal/modal-pay-medical-time/modal-pay-medical-time.component';
import { DatePipe } from '@angular/common';
import { ModalCancelMedicalTimeComponent } from '../../modal/modal-cancel-medical-time/modal-cancel-medical-time.component';

@Component({
      selector: 'app-table-medical-hours-payment',
      templateUrl: './table-medical-hours-payment.component.html',
      styleUrls: ['./table-medical-hours-payment.component.css'],
      providers:[
            BillService
      ]
})
export class TableMedicalHoursPaymentComponent implements OnInit {

      model: any = {};
      dateNow = new Date();
      fullYear: string;
      timeNow: string;

      currentPageBill: number = 1;
      countRowsByPageBill: number = 5;
      countRowsBill: number;

      currentPagePatient: number = 1;
      countRowsByPagePatient: number = 5;
      countRowsPatient: number;

      loadingBillFindAll: boolean;
      loadingBillByPatient: boolean;

      bills: Array<any> = new Array<any>();
      billsPatient: Array<any> = new Array<any>();

      constructor(private _billService: BillService, private _modalService: NgbModal, private router: Router, private datePipe: DatePipe) { }

      ngOnInit() {
            this.getDateNow();
            this.billFindAllByStateMedicalTimeReservedAndStatusActive();
      }

      validatePayMedicalTime(billMedicalTime: string, scheduleHour: string){

            billMedicalTime = this.transformBillMedicalTime(billMedicalTime);

            if(billMedicalTime == this.fullYear && scheduleHour > this.timeNow){

                  return true;

            }else if(billMedicalTime > this.fullYear){

                  return true;

            }else{

                  return false;

            }

      }

      getDateNow(){

            let year: string = this.dateNow.getFullYear().toString();
            let month: string = (this.dateNow.getMonth() + 1).toString();
            let day: string = this.dateNow.getDate().toString();
            
            let time: string = this.dateNow.getHours() + ':' + this.dateNow.getMinutes();

            month = month.length == 1 ? '0' + month : month;
            day = day.length == 1 ? '0' + day : day;

            this.fullYear = year + '-' + month + '-' + day;
            this.timeNow = time;
      }

      transformBillMedicalTime(billMedicalTime: string){
            return this.datePipe.transform(billMedicalTime, 'yyyy-MM-dd');
      }

      pageChangePatient(e){

            this.currentPagePatient = e - 1;
            this.loadingBillByPatient = true;
            this.model.patientRun = this.model.patientRun == "" ? undefined : this.model.patientRun;
            this._billService.findAllByStateMedicalTimeReservedAndPatientRunAndStatusActive(this.model.patientRun, this.currentPagePatient).subscribe(res => {
                  if(res.statusCode == 200){

                        this.billsPatient = res.bill;
                        this.currentPagePatient = res.currentPage + 1;
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

      pageChangeBill(e){

            this.currentPageBill = e - 1;
            this.loadingBillFindAll = true;
            this._billService.findAllByStateMedicalTimeReservedAndStatusActive(this.currentPageBill).subscribe(res => {
                  if(res.statusCode == 200){
                        this.bills = res.bill;
                        this.currentPageBill = res.currentPage + 1;
                        this.loadingBillFindAll = false;

                  }else if(res.statusCode == 204){

                        this.bills.length = 0;
                        this.loadingBillFindAll = false;

                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);

                  }else if(res.statusCode == 500){

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }
            })
      }

      billFindAllByStateMedicalTimeReservedAndStatusActive(){

            this.currentPageBill = this.currentPageBill - 1;
            this.loadingBillFindAll = true;
            this._billService.findAllByStateMedicalTimeReservedAndStatusActive(this.currentPageBill).subscribe(res => {
                  if(res.statusCode == 200){
                        this.bills = res.bill;
                        this.currentPageBill = res.currentPage;
                        this.countRowsBill = res.countRows;
                        this.countRowsByPageBill = res.countRowsByPage;
                        this.loadingBillFindAll = false;

                  }else if(res.statusCode == 204){

                        this.bills.length = 0;
                        this.loadingBillFindAll = false;

                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);

                  }else if(res.statusCode == 500){

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }
            })
      }

      billFindAllByPatientRunAndByStateMedicalTimeReservedAndStatusActive(){

            this.currentPagePatient = this.currentPagePatient - 1;
            this.currentPagePatient = this.currentPagePatient < 0 ? 0 : this.currentPagePatient;
            this.loadingBillByPatient = true;
            this.model.patientRun = this.model.patientRun == "" ? undefined : this.model.patientRun;
            this._billService.findAllByStateMedicalTimeReservedAndPatientRunAndStatusActive(this.model.patientRun, this.currentPagePatient).subscribe(res => {
                  if(res.statusCode == 200){

                        this.billsPatient = res.bill;
                        this.currentPagePatient = res.currentPage;
                        this.countRowsPatient = res.countRows;
                        this.countRowsByPagePatient = res.countRowsByPage;
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

      cancelMedicalTime(billId: number){

            let objectBillPatient : any = this.bills.filter(res => res.billId == billId);
            const modalRef = this._modalService.open(ModalCancelMedicalTimeComponent);
            modalRef.componentInstance.objectBillPatient = objectBillPatient;
            modalRef.result.then((result) => {
                  this.currentPageBill = 1;
                  this.countRowsByPageBill = 5;

                  this.currentPagePatient = 1;
                  this.countRowsByPagePatient = 5;
                  this.billFindAllByStateMedicalTimeReservedAndStatusActive();
                  this.billFindAllByPatientRunAndByStateMedicalTimeReservedAndStatusActive();
            }).catch((error) => {
                  this.currentPageBill = 1;
                  this.countRowsByPageBill = 5;

                  this.currentPagePatient = 1;
                  this.countRowsByPagePatient = 5;
                  this.billFindAllByStateMedicalTimeReservedAndStatusActive();
                  this.billFindAllByPatientRunAndByStateMedicalTimeReservedAndStatusActive();      
            });

      }

      cancelMedicalTimePatient(billId: number){

            let objectBillPatient : any = this.billsPatient.filter(res => res.billId == billId);
            const modalRef = this._modalService.open(ModalCancelMedicalTimeComponent);
            modalRef.componentInstance.objectBillPatient = objectBillPatient;
            modalRef.result.then((result) => {
                  this.currentPageBill = 1;
                  this.countRowsByPageBill = 5;

                  this.currentPagePatient = 1;
                  this.countRowsByPagePatient = 5;
                  this.billFindAllByStateMedicalTimeReservedAndStatusActive();
                  this.billFindAllByPatientRunAndByStateMedicalTimeReservedAndStatusActive();      
            }).catch((error) => {
                  this.currentPageBill = 1;
                  this.countRowsByPageBill = 5;

                  this.currentPagePatient = 1;
                  this.countRowsByPagePatient = 5;
                  this.billFindAllByStateMedicalTimeReservedAndStatusActive();
                  this.billFindAllByPatientRunAndByStateMedicalTimeReservedAndStatusActive();
            });

      }

      payMedicalTime(billId: number){

            const modalRef = this._modalService.open(ModalPayMedicalTimeComponent);
            modalRef.componentInstance.billId = billId;
            modalRef.componentInstance.objectBill = this.bills.filter(res => res.billId == billId);
            modalRef.result.then((result) => {
                        this.currentPageBill = 1;
                        this.countRowsByPageBill = 5;

                        this.currentPagePatient = 1;
                        this.countRowsByPagePatient = 5;
                        this.billFindAllByStateMedicalTimeReservedAndStatusActive();
                        this.billFindAllByPatientRunAndByStateMedicalTimeReservedAndStatusActive();
                }).catch((error) => {
                        this.currentPageBill = 1;
                        this.countRowsByPageBill = 5;

                        this.currentPagePatient = 1;
                        this.countRowsByPagePatient = 5;
                        this.billFindAllByStateMedicalTimeReservedAndStatusActive();
                        this.billFindAllByPatientRunAndByStateMedicalTimeReservedAndStatusActive();
                });

      }

      payMedicalTimePatient(billId: number){

            const modalRef = this._modalService.open(ModalPayMedicalTimeComponent);
            modalRef.componentInstance.billId = billId;
            modalRef.componentInstance.objectBill = this.billsPatient.filter(res => res.billId == billId);
            modalRef.result.then((result) => {
                        this.currentPageBill = 1;
                        this.countRowsByPageBill = 5;

                        this.currentPagePatient = 1;
                        this.countRowsByPagePatient = 5;
                        this.billFindAllByStateMedicalTimeReservedAndStatusActive();
                        this.billFindAllByPatientRunAndByStateMedicalTimeReservedAndStatusActive();
                }).catch((error) => {
                        this.currentPageBill = 1;
                        this.countRowsByPageBill = 5;

                        this.currentPagePatient = 1;
                        this.countRowsByPagePatient = 5;
                        this.billFindAllByStateMedicalTimeReservedAndStatusActive();
                        this.billFindAllByPatientRunAndByStateMedicalTimeReservedAndStatusActive();
                });

      }

}
