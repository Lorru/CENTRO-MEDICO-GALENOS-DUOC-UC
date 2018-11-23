import { Component, OnInit } from '@angular/core';
import { ModalErrorComponent } from '../modal-error/modal-error.component';
import { GenderService } from '../../../Services/gender.service';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BranchOfficeService } from '../../../Services/branchOffice.service';
import { Router } from '@angular/router';
import { ModalSuccessComponent } from '../modal-success/modal-success.component';
import { PersonalService } from '../../../Services/personal.service';
import { CategoryService } from '../../../Services/category.service';

@Component({
    selector: 'app-modal-edit-personal',
    templateUrl: './modal-edit-personal.component.html',
    styleUrls: ['./modal-edit-personal.component.css'],
    providers: [
        GenderService,
        BranchOfficeService,
        CategoryService,
        PersonalService
    ]
})
export class ModalEditPersonalComponent implements OnInit {

    loadingGender: boolean;
    loadingBranchOffice: boolean;
    loadingCategory: boolean;
    loadingUpdatePersonal: boolean;

    model: any = {};
    objectPersonal: any;

    genders: Array<any> = new Array<any>();
    branchOffices: Array<any> = new Array<any>();
    categorys: Array<any> = new Array<any>();

    constructor(public activeModal: NgbActiveModal, private _genderService: GenderService, private _modalService: NgbModal, private _branchOfficeService: BranchOfficeService, private router: Router, private _categoryService: CategoryService, private _personalService: PersonalService) { }

    ngOnInit() {
          
      this.genderFindAll();
      this.branchOfficeFindAll();
      this.valueModel();

    }

    valueModel(){

      this.model.personalRun = this.objectPersonal.personalId.personalRun;
      this.model.personalFirstName = this.objectPersonal.personalId.personalFirstName;
      this.model.personalSecondName = this.objectPersonal.personalId.personalSecondName;
      this.model.personalSurName = this.objectPersonal.personalId.personalSurName;
      this.model.personalSecondSurName = this.objectPersonal.personalId.personalSecondSurName;
      this.model.personalBirthDate = this.returnPersonalBirthDate(this.objectPersonal);
      this.model.personalEmail = this.objectPersonal.personalId.personalEmail;
      this.model.genderId = this.objectPersonal.personalId.genderId.genderId;
      this.model.branchOfficeId = this.objectPersonal.personalId.branchOfficeId.branchOfficeId;
      this.model.categoryId = this.objectPersonal.personalId.categoryId.categoryId;
      this.model.personalId = this.objectPersonal.personalId.personalId;
      this.model.userId = this.objectPersonal.userId;

    }

    returnPersonalBirthDate(objectPersonal: any){

      let year: string = objectPersonal.personalId.personalBirthDate[0];
      let month: string = objectPersonal.personalId.personalBirthDate[1].toString().length == 1 ? '0' + objectPersonal.personalId.personalBirthDate[1] : objectPersonal.personalId.personalBirthDate[1];
      let day: string = objectPersonal.personalId.personalBirthDate[2].toString().length == 1 ? '0' + objectPersonal.personalId.personalBirthDate[2] : objectPersonal.personalId.personalBirthDate[2];

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
                  this.categoryFindByBranchOfficeId();

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

    categoryFindByBranchOfficeId(){

      this.loadingCategory = true;
      this._categoryService.findByBranchOfficeId(this.model.branchOfficeId).subscribe(res => { 

            if(res.statusCode == 200){

                  this.categorys = res.category;
                  this.loadingCategory = false;

            }else if(res.statusCode == 204){

                  this.categorys.length = 0;
                  this.loadingCategory = false;

            }else if(res.statusCode == 500){

                  this.loadingCategory = false;

                  const modalRef = this._modalService.open(ModalErrorComponent);
                  modalRef.componentInstance.message = res.message;

            }else if(res.statusCode == 401){

                  localStorage.clear();
                  this.router.navigate(['/']);
            }
      })
    }

    personalUpdate(){
      
      this.loadingUpdatePersonal = true;
      this.model.profileId = this.valueProfileId();
      this._personalService.updatePersonalByPersonalId(this.model.userId, this.model.personalId, this.model.personalRun, this.model.personalFirstName, this.model.personalSecondName, this.model.personalSurName, this.model.personalSecondSurName, this.model.personalBirthDate, this.model.personalEmail, this.model.userPassword, this.model.genderId, this.model.branchOfficeId, this.model.categoryId, this.model.profileId).subscribe(res => {

        if(res.statusCode == 200){

          this.loadingUpdatePersonal = false;
          
          const modalRef = this._modalService.open(ModalSuccessComponent);
          modalRef.componentInstance.message = 'Se actualizaron los datos correctamente.';
          modalRef.componentInstance.title = 'Actualización Exitosa.';
          modalRef.result.then((result) => {
                this.dismiss();
          }).catch((error) => {
                this.dismiss();
          });

        }else if(res.statusCode == 204){

          this.loadingUpdatePersonal = false;

          const modalRef = this._modalService.open(ModalErrorComponent);
          modalRef.componentInstance.message = res.message;

        }else if(res.statusCode == 500){

          this.loadingUpdatePersonal = false;

          const modalRef = this._modalService.open(ModalErrorComponent);
          modalRef.componentInstance.message = res.message;

        }else if(res.statusCode == 401){

          this.dismiss();
          localStorage.clear();
          this.router.navigate(['/']);
          
        }

      })

    }

    valueProfileId(){

        if(this.model.categoryId == 1){

            return 3;

        }else if(this.model.categoryId == 2){

          return 4;

        }else if(this.model.categoryId == 3){

            return 1;
        }

    }

}
