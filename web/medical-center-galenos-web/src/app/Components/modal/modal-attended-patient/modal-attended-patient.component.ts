import { Component, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BillService } from '../../../Services/bill.service';
import { Router } from '@angular/router';
import { ModalErrorComponent } from '../modal-error/modal-error.component';
import { ModalSuccessComponent } from '../modal-success/modal-success.component';

@Component({
    selector: 'app-modal-attended-patient',
    templateUrl: './modal-attended-patient.component.html',
    styleUrls: ['./modal-attended-patient.component.css'],
    providers:[
        BillService
    ]
})
export class ModalAttendedPatientComponent implements OnInit {

    message: string;
    objectPatient: any;
    loadingAttended: boolean;

    constructor(public activeModal: NgbActiveModal, private _billService: BillService, private _modalService: NgbModal, private router: Router) { }

    ngOnInit() {

        this.getMessage();

    }

    getMessage(){

        this.message = "¿Seguro que quiere atender al paciente " + this.objectPatient[0].patientId.patientFullName + " ?.";

    }

    dismiss() {
        this.activeModal.dismiss('Modal Dismiss');
    }


    attendedPatient(){

        this.loadingAttended = true;
            this._billService.updateBillByBillIdToStateMedicalTimeAttended(this.objectPatient[0].billId).subscribe(res => {
                  if(res.statusCode == 200){

                        this.loadingAttended = false;

                        const modalRef = this._modalService.open(ModalSuccessComponent);
                        modalRef.componentInstance.message = res.message;
                        modalRef.componentInstance.title = "Atender Paciente.";
                        modalRef.result.then((result) => {
                            this.dismiss();
                        }).catch((error) => {
                            this.dismiss();
                        });

                  }else if(res.statusCode == 204){

                        this.loadingAttended = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 500){

                        this.loadingAttended = false;

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
