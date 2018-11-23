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
    selector: 'app-modal-edit-specialist',
    templateUrl: './modal-edit-specialist.component.html',
    styleUrls: ['./modal-edit-specialist.component.css'],
    providers: [
        GenderService,
        BranchOfficeService,
        SpecialtyService,
        SpecialistService
    ]
})
export class ModalEditSpecialistComponent implements OnInit {

    loadingGender: boolean;
    loadingBranchOffice: boolean;
    loadingSpecialty: boolean;
    loadingUpdateSpecialist: boolean;

    model: any = {};
    objectSpecialist: any;

    genders: Array<any> = new Array<any>();
    branchOffices: Array<any> = new Array<any>();
    specialtys: Array<any> = new Array<any>();

    constructor(public activeModal: NgbActiveModal, private _genderService: GenderService, private _modalService: NgbModal, private _branchOfficeService: BranchOfficeService, private router: Router, private _specialtyService: SpecialtyService, private _specialistService: SpecialistService) { }

    ngOnInit() {

      this.genderFindAll();
      this.branchOfficeFindAll();
      this.valueModel();
    }

    valueModel(){

      this.model.specialistRun = this.objectSpecialist.specialistId.specialistRun;
      this.model.specialistFirstName = this.objectSpecialist.specialistId.specialistFirstName;
      this.model.specialistSecondName = this.objectSpecialist.specialistId.specialistSecondName;
      this.model.specialistSurName = this.objectSpecialist.specialistId.specialistSurName;
      this.model.specialistSecondSurName = this.objectSpecialist.specialistId.specialistSecondSurName;
      this.model.specialistBirthDate = this.returnSpecialistBirthDate(this.objectSpecialist);
      this.model.specialistEmail = this.objectSpecialist.specialistId.specialistEmail;
      this.model.genderId = this.objectSpecialist.specialistId.genderId.genderId;
      this.model.branchOfficeId = this.objectSpecialist.specialistId.branchOfficeId.branchOfficeId;
      this.model.specialtyId = this.objectSpecialist.specialistId.specialtyId.specialtyId;
      this.model.specialistId = this.objectSpecialist.specialistId.specialistId;
      this.model.userId = this.objectSpecialist.userId;

    }

    returnSpecialistBirthDate(objectSpecialist: any){

      let year: string = objectSpecialist.specialistId.specialistBirthDate[0];
      let month: string = objectSpecialist.specialistId.specialistBirthDate[1].toString().length == 1 ? '0' + objectSpecialist.specialistId.specialistBirthDate[1] : objectSpecialist.specialistId.specialistBirthDate[1];
      let day: string = objectSpecialist.specialistId.specialistBirthDate[2].toString().length == 1 ? '0' + objectSpecialist.specialistId.specialistBirthDate[2] : objectSpecialist.specialistId.specialistBirthDate[2];

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

    branchOfficeFindAll(){

      this.loadingBranchOffice = true;
      this._branchOfficeService.findAll().subscribe(res => {

            if(res.statusCode == 200){

                  this.branchOffices = res.branchOffice;
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

    specialistUpdate(){
      
      this.loadingUpdateSpecialist = true;
      this._specialistService.updateSpecialistBySpecialistId(this.model.userId, this.model.specialistId, this.model.specialistRun, this.model.specialistFirstName, this.model.specialistSecondName, this.model.specialistSurName, this.model.specialistSecondSurName, this.model.specialistBirthDate, this.model.specialistEmail, this.model.userPassword, this.model.genderId, this.model.branchOfficeId, this.model.specialtyId).subscribe(res => {

        if(res.statusCode == 200){

          this.loadingUpdateSpecialist = false;
          
          const modalRef = this._modalService.open(ModalSuccessComponent);
          modalRef.componentInstance.message = 'Se actualizaron los datos correctamente.';
          modalRef.componentInstance.title = 'Actualización Exitosa.';
          modalRef.result.then((result) => {
                this.dismiss();
          }).catch((error) => {
                this.dismiss();
          });

        }else if(res.statusCode == 204){

          this.loadingUpdateSpecialist = false;

          const modalRef = this._modalService.open(ModalErrorComponent);
          modalRef.componentInstance.message = res.message;

        }else if(res.statusCode == 500){

          this.loadingUpdateSpecialist = false;

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
