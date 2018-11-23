import { Component, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ForecastService } from '../../../Services/forecast.service';
import { PaymentTypeService } from '../../../Services/paymentType.service';
import { BillService } from '../../../Services/bill.service';
import { ModalErrorComponent } from '../modal-error/modal-error.component';
import { Router } from '@angular/router';
import { ModalSuccessComponent } from '../modal-success/modal-success.component';

@Component({
      selector: 'app-modal-pay-medical-time',
      templateUrl: './modal-pay-medical-time.component.html',
      styleUrls: ['./modal-pay-medical-time.component.css'],
      providers: [
            ForecastService,
            PaymentTypeService,
            BillService
      ]
})
export class ModalPayMedicalTimeComponent implements OnInit {

      billId: number;
      model: any = {};
      objectBill: any;
      discountMedicalCenter: number = 50;
      billCommisions: number = 30;

      loadingPaymentType: boolean;
      loadingForecast: boolean;
      loadingPayMedicalTime: boolean;

      paymentTypes: Array<any> = new Array<any>();
      forecasts: Array<any> = new Array<any>();

      constructor(public activeModal: NgbActiveModal, private _forecastService: ForecastService, private _paymentTypeService: PaymentTypeService, private _billService: BillService, private _modalService: NgbModal, private router: Router) { }

      ngOnInit() {
            this.paymentTypeFindAll();
            this.forecastFindAll();
            this.returnSpecialtyAmount();
            this.returnSpecialtyDescription();
      }

      returnSpecialtyAmount(){

            this.model.specialtyAmount = this.objectBill[0].specialistId.specialtyId.specialtyAmount;
      }

      returnBillSalary(){
            let total: number = this.model.paymentTypeAmount + this.model.forecastAmount + this.model.specialtyAmount;

            this.model.billSalary = (total * this.discountMedicalCenter) / 100;
      }

      returnSpecialtyDescription(){

            this.model.specialtyDescription = this.objectBill[0].specialistId.specialtyId.specialtyDescription;
      }

      returnForecastAmount(forecastId: number){

            let objectForecast = this.forecasts.filter(res => res.forecastId == forecastId);

            this.model.forecastAmount = objectForecast[0].forecastAmount;
      }

      returnPaymentTypeAmount(paymentTypeId: number){

            let objectPaymentType = this.paymentTypes.filter(res => res.paymentTypeId == paymentTypeId);

            this.model.paymentTypeAmount = objectPaymentType[0].paymentTypeAmount;
      }

      payMedicalTime(){
            
            this.loadingPayMedicalTime = true;
            this.model.billCommissions = (this.model.billSalary * this.billCommisions) / 100;
            this._billService.updateBillByBillIdToStateMedicalTimeAndToForecastAndToPaymentTypeAndBillCommissionsAndBillSalary(this.billId, this.model.paymentTypeId, this.model.forecastId, this.model.billCommissions, this.model.billSalary).subscribe(res => {
                  
                  if(res.statusCode == 200){
                        
                        this.loadingPayMedicalTime = false;

                        let link = document.createElement("a");
                        link.href = "data:application/pdf;base64," + String(res.voucher);
                        link.download = res.voucherName + res.extention;
                        link.dispatchEvent(new MouseEvent("click"));

                        const modalRef = this._modalService.open(ModalSuccessComponent);
                        modalRef.componentInstance.message = res.message;
                        modalRef.componentInstance.title = "Pago de Hora Médica.";
                        this.dismiss();

                  }else if(res.statusCode == 204){

                        this.loadingPayMedicalTime = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 500){

                        this.loadingPaymentType = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 401){

                        this.dismiss();
                        localStorage.clear();
                        this.router.navigate(['/']);
                  }
            })
      }
  
      dismiss() {
          this.activeModal.dismiss('Modal Dismiss');
      }

      paymentTypeFindAll(){

            this.loadingPaymentType = true;
            this._paymentTypeService.findAll().subscribe(res => {
                  if(res.statusCode == 200){

                        this.paymentTypes = res.paymentType;
                        this.model.paymentTypeId = res.paymentType[0].paymentTypeId;
                        this.returnPaymentTypeAmount(this.model.paymentTypeId);
                        this.returnBillSalary();
                        this.loadingPaymentType = false;

                  }else if(res.statusCode == 204){

                        this.paymentTypes.length = 0;
                        this.loadingPaymentType = false;

                  }else if(res.statusCode == 500){

                        this.loadingPaymentType = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);
                  }
            })
      }

      forecastFindAll(){

            this.loadingForecast = true;
            this._forecastService.findAll().subscribe(res => {
                  if(res.statusCode == 200){

                        this.forecasts = res.forecast;
                        this.model.forecastId = res.forecast[0].forecastId;
                        this.returnForecastAmount(this.model.forecastId);
                        this.returnBillSalary();
                        this.loadingForecast = false;

                  }else if(res.statusCode == 204){

                        this.forecasts.length = 0;
                        this.loadingForecast = false;

                  }else if(res.statusCode == 500){

                        this.loadingForecast = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);
                  }
            })
      }
      
}
