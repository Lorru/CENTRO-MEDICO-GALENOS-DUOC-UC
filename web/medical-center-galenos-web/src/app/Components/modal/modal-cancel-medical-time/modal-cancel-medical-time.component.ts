import { Component, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BillService } from '../../../Services/bill.service';
import { ModalSuccessComponent } from '../modal-success/modal-success.component';
import { ModalErrorComponent } from '../modal-error/modal-error.component';
import { Router } from '@angular/router';

@Component({
    selector: 'app-modal-cancel-medical-time',
    templateUrl: './modal-cancel-medical-time.component.html',
    styleUrls: ['./modal-cancel-medical-time.component.css'],
    providers:[
        BillService
    ]
})
export class ModalCancelMedicalTimeComponent implements OnInit {

    objectBillPatient : any;
    message: string;
    loadingCancelMedicalTime: boolean;

    constructor(public activeModal: NgbActiveModal, private datePipe: DatePipe, private _billService: BillService, private _modalService: NgbModal, private router: Router) { }

    ngOnInit() {

        this.getMessages();        

    }

    getMessages(){

        this.message = '¿Seguro que quiere cancelar la hora médica para el día ' + this.transformBillMedicalTime(this.objectBillPatient[0].billMedicalTime) + ' con el especialista ' + this.objectBillPatient[0].specialistId.specialistFullName + '?';

    }

    transformBillMedicalTime(billMedicalTime: string){
        return this.datePipe.transform(billMedicalTime, 'yyyy-MM-dd');
    }

    cancelMedicalTime(){

        this.loadingCancelMedicalTime = true;
        this._billService.updateBillByBillIdToBillStatusInactivo(this.objectBillPatient[0].billId).subscribe(res => {

            if(res.statusCode == 200){

                this.loadingCancelMedicalTime = false;

                const modalRef = this._modalService.open(ModalSuccessComponent);
                modalRef.componentInstance.message = res.message;
                modalRef.componentInstance.title = "Anular Hora Médica.";
                this.dismiss();

          }else if(res.statusCode == 204){

                this.loadingCancelMedicalTime = false;

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;

          }else if(res.statusCode == 500){

                this.loadingCancelMedicalTime = false;

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

}
