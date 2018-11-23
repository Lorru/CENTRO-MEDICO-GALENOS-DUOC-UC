import { Component, OnInit } from '@angular/core';
import { ModalErrorComponent } from '../modal-error/modal-error.component';
import { GenderService } from '../../../Services/gender.service';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BranchOfficeService } from '../../../Services/branchOffice.service';
import { Router } from '@angular/router';
import { SpecialtyService } from '../../../Services/specialty.service';
import { SpecialistService } from '../../../Services/specialist.service';
import { ModalSuccessComponent } from '../modal-success/modal-success.component';

@Component({
    selector: 'app-modal-create-specialist',
    templateUrl: './modal-create-specialist.component.html',
    styleUrls: ['./modal-create-specialist.component.css'],
    providers: [
        GenderService,
        BranchOfficeService,
        SpecialtyService,
        SpecialistService
    ]
})
export class ModalCreateSpecialistComponent implements OnInit {

    loadingGender: boolean;
    loadingBranchOffice: boolean;
    loadingSpecialty: boolean;
    loadingCreateSpecialist: boolean;

    model: any = {};

    genders: Array<any> = new Array<any>();
    branchOffices: Array<any> = new Array<any>();
    specialtys: Array<any> = new Array<any>();

    constructor(public activeModal: NgbActiveModal, private _genderService: GenderService, private _modalService: NgbModal, private _branchOfficeService: BranchOfficeService, private router: Router, private _specialtyService: SpecialtyService, private _specialistService: SpecialistService) { }

    ngOnInit() {

      this.genderFindAll();
      this.branchOfficeFindAll();

    }

    dismiss() {
            this.activeModal.dismiss('Modal Dismiss');
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

    specialtyFindByBranchOfficeId(){

      this.loadingSpecialty = true;
      this._specialtyService.findByBranchOfficeId(this.model.branchOfficeId).subscribe(res => { 

            if(res.statusCode == 200){

                  this.specialtys = res.specialty;
                  this.model.specialtyId = res.specialty[0].specialtyId;
                  this.loadingSpecialty = false;

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

    specialistCreate(){
      
      this.loadingCreateSpecialist = true;
      this._specialistService.create(this.model.specialistRun, this.model.specialistFirstName, this.model.specialistSecondName, this.model.specialistSurName, this.model.specialistSecondSurName, this.model.specialistBirthDate, this.model.specialistEmail, this.model.userPassword, this.model.genderId, this.model.branchOfficeId, this.model.specialtyId).subscribe(res => {
          if(res.statusCode == 201){

            this.loadingCreateSpecialist = false;
            
            const modalRef = this._modalService.open(ModalSuccessComponent);
            modalRef.componentInstance.message = 'Se registro el especialista ' + res.specialistNew.specialistFullName + ' correctamente.';
            modalRef.componentInstance.title = 'Registro Exitoso.';
            modalRef.result.then((result) => {
                  this.dismiss();
            }).catch((error) => {
                  this.dismiss();
            });

          }else if(res.statusCode == 200){

            this.loadingCreateSpecialist = false;

            const modalRef = this._modalService.open(ModalErrorComponent);
            modalRef.componentInstance.message = res.message;

          }else if(res.statusCode == 204){

            this.loadingCreateSpecialist = false;

            const modalRef = this._modalService.open(ModalErrorComponent);
            modalRef.componentInstance.message = res.message;

          }else if(res.statusCode == 500){

            this.loadingCreateSpecialist = false;

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
