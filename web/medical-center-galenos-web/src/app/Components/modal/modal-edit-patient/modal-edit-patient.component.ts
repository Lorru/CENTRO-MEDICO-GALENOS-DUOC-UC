import { Component, OnInit } from '@angular/core';
import { ModalErrorComponent } from '../modal-error/modal-error.component';
import { GenderService } from '../../../Services/gender.service';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { ModalSuccessComponent } from '../modal-success/modal-success.component';
import { PatientService } from '../../../Services/patient.service';

@Component({
    selector: 'app-modal-edit-patient',
    templateUrl: './modal-edit-patient.component.html',
    styleUrls: ['./modal-edit-patient.component.css'],
    providers: [
        GenderService,
        PatientService
    ]
})
export class ModalEditPatientComponent implements OnInit {

    loadingGender: boolean;
    loadingUpdatePatient: boolean;

    model: any = {};
    objectPatient: any;

    genders: Array<any> = new Array<any>();

    constructor(public activeModal: NgbActiveModal, private _genderService: GenderService, private _modalService: NgbModal, private router: Router, private _patientService: PatientService) { }

    ngOnInit() {
      this.genderFindAll();
      this.valueModel();
    }

    valueModel(){

      this.model.patientRun = this.objectPatient.patientId.patientRun;
      this.model.patientFirstName = this.objectPatient.patientId.patientFirstName;
      this.model.patientSecondName = this.objectPatient.patientId.patientSecondName;
      this.model.patientSurName = this.objectPatient.patientId.patientSurName;
      this.model.patientSecondSurName = this.objectPatient.patientId.patientSecondSurName;
      this.model.patientBirthDate = this.returnPatientBirthDate(this.objectPatient);
      this.model.patientEmail = this.objectPatient.patientId.patientEmail;
      this.model.genderId = this.objectPatient.patientId.genderId.genderId;
      this.model.patientId = this.objectPatient.patientId.patientId;
      this.model.userId = this.objectPatient.userId;
    }

    returnPatientBirthDate(objectPatient: any){

        let year: string = objectPatient.patientId.patientBirthDate[0];
        let month: string = objectPatient.patientId.patientBirthDate[1].toString().length == 1 ? '0' + objectPatient.patientId.patientBirthDate[1] : objectPatient.patientId.patientBirthDate[1];
        let day: string = objectPatient.patientId.patientBirthDate[2].toString().length == 1 ? '0' + objectPatient.patientId.patientBirthDate[2] : objectPatient.patientId.patientBirthDate[2];

        return year + '-' + month + '-' + day;
    }

    dismiss() {
            this.activeModal.dismiss('Modal Dismiss');
    }

    genderFindAll(){
        
      this.loadingGender = true;
      this._genderService.findAll().subscribe(res => {

          if(res.statusCode == 200){

              this.genders = res.gender;
              this.loadingGender = false;

          }else if(res.statusCode == 204){

              this.genders.length = 0;
              this.loadingGender = false;

          }else if(res.statusCode == 500){

              this.loadingGender = false;

              const modalRef = this._modalService.open(ModalErrorComponent);
              modalRef.componentInstance.message = res.message;

          }

      })
    }

    patientUpdate(){
      
      this.loadingUpdatePatient = true;
      this._patientService.updatePatientByPatientId(this.model.userId, this.model.patientId, this.model.patientRun, this.model.patientFirstName, this.model.patientSecondName, this.model.patientSurName, this.model.patientSecondSurName, this.model.patientBirthDate, this.model.patientEmail, this.model.userPassword, this.model.genderId).subscribe(res => {

          if(res.statusCode == 200){

            this.loadingUpdatePatient = false;
            
            const modalRef = this._modalService.open(ModalSuccessComponent);
            modalRef.componentInstance.message = 'Se actualizaron los datos correctamente.';
            modalRef.componentInstance.title = 'Actualización Exitosa.';
            modalRef.result.then((result) => {
                  this.dismiss();
            }).catch((error) => {
                  this.dismiss();
            });

          }else if(res.statusCode == 204){

            this.loadingUpdatePatient = false;

            const modalRef = this._modalService.open(ModalErrorComponent);
            modalRef.componentInstance.message = res.message;

          }else if(res.statusCode == 500){

            this.loadingUpdatePatient = false;

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
