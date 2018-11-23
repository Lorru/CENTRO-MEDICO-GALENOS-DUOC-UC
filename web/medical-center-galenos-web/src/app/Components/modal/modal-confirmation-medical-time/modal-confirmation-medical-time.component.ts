import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BillService } from '../../../Services/bill.service';
import { ModalSuccessComponent } from '../modal-success/modal-success.component';
import { ModalErrorComponent } from '../modal-error/modal-error.component';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
      selector: 'app-modal-confirmation-medical-time',
      templateUrl: './modal-confirmation-medical-time.component.html',
      styleUrls: ['./modal-confirmation-medical-time.component.css'],
      providers: [
            BillService
      ]
})
export class ModalConfirmationMedicalTimeComponent implements OnInit {

      objectBranchOffice: any;
      objectSpecialty: any;
      objectSpecialist: any;
      objectSchedule: any;
      objectPatient: any;
      billMedicalTime: string;
      patientId: number;
      formRegisterMedicalTimeComponent: any;

      loadingBillCreate: boolean;

      constructor(public activeModal: NgbActiveModal, private _billService: BillService, private _modalService: NgbModal, private router: Router) { }

      ngOnInit() {

            this.patientId = JSON.parse(localStorage.getItem('patientId')) == null ? this.objectPatient[0].patientId : JSON.parse(localStorage.getItem('patientId'));
      }

      dismiss() {

            this.activeModal.dismiss('Modal Dismiss');
            this.formRegisterMedicalTimeComponent.getMinMedicalTime();
            this.formRegisterMedicalTimeComponent.branchOfficeFindAll();
      }


      billCreate(){

            this.loadingBillCreate = true;
            this._billService.create(this.patientId, this.objectSpecialist[0].specialistId, this.objectSchedule[0].scheduleId, this.billMedicalTime).subscribe(res => {

                  if(res.statusCode == 201){
                        
                        this.loadingBillCreate = false;
                        const modalRef = this._modalService.open(ModalSuccessComponent);
                        modalRef.componentInstance.message = JSON.parse(localStorage.getItem('patientId')) == null ? 'Se registro con exito la reserva para el paciente ' + this.objectPatient[0].patientFullName + ', ahora le llegara un correo con la información de la reserva de hora médica a la siguiente dirección de correo ' + this.objectPatient[0].patientEmail + '.' :  'Tu reserva se registro con exito, puedes revisar tu correo con la información de tu reserva de hora médica.';
                        modalRef.componentInstance.title = 'Reserva Registrada con Exito.';
                        this.dismiss();

                  }else if(res.statusCode == 204){

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;
                        this.dismiss();

                  }else if(res.statusCode == 401){

                        this.dismiss();
                        localStorage.clear();
                        this.router.navigate(['/']);

                  }else if(res.statusCode == 500){

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;
                        this.dismiss();
                  }
            })
      }

}
