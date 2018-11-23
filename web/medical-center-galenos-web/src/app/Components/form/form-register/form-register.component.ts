import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenderService } from '../../../Services/gender.service';
import { PatientService } from '../../../Services/patient.service';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';
import { ModalSuccessComponent } from '../../modal/modal-success/modal-success.component';

@Component({
    selector: 'app-form-register',
    templateUrl: './form-register.component.html',
    styleUrls: ['./form-register.component.css'],
    providers: [
        GenderService,
        PatientService
    ]
})
export class FormRegisterComponent implements OnInit {

    loadingGender: boolean;
    loadingCreatePatient: boolean;
    model: any = {};
    genders: Array<any> = new Array<any>();

    constructor(private _genderService: GenderService, private _patientService: PatientService, private _modalService: NgbModal) { }

    ngOnInit() {

        this.genderFindAll();
    }

    genderFindAll(){
        
        this.loadingGender = true;
        this._genderService.findAll().subscribe(res => {

            if(res.statusCode == 200){

                this.genders = res.gender;
                this.model.genderId = res.gender[0].genderId;
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

    createPatient(){

        this.loadingCreatePatient = true;
        this._patientService.create(this.model.patientRun, this.model.patientFirstName, this.model.patientSecondName, this.model.patientSurName, this.model.patientSecondSurName, this.model.patientBirthDate, this.model.patientEmail, this.model.userPassword, this.model.genderId).subscribe(res => {

            if(res.statusCode == 201){

                this.loadingCreatePatient = false;
                
                this.cleanInput();

                const modalRef = this._modalService.open(ModalSuccessComponent);
                modalRef.componentInstance.message = 'Gracias por registrarte ' + res.patientNew.patientFullName + '.';
                modalRef.componentInstance.title = 'Registro Exitoso.';

            }else if(res.statusCode == 200){

                this.loadingCreatePatient = false;

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;

            }else if(res.statusCode == 204){

                this.loadingCreatePatient = false;

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;

            }else if(res.statusCode == 500){

                this.loadingCreatePatient = false;

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;
            }
        })
    }

    cleanInput(){
        this.model.patientRun = undefined;
        this.model.patientFirstName = undefined;
        this.model.patientSecondName = undefined;
        this.model.patientSurName = undefined;
        this.model.patientSecondSurName = undefined;
        this.model.patientBirthDate = undefined;
        this.model.patientEmail = undefined;
        this.model.userPassword = undefined;
    }
}
