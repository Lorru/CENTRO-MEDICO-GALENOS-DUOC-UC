import { Component, OnInit } from '@angular/core';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';
import { BranchOfficeService } from '../../../Services/branchOffice.service';
import { SpecialistService } from '../../../Services/specialist.service';
import { SpecialtyService } from '../../../Services/specialty.service';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BillService } from '../../../Services/bill.service';

@Component({
  selector: 'app-form-bill',
  templateUrl: './form-bill.component.html',
  styleUrls: ['./form-bill.component.css'],
  providers: [
            BranchOfficeService,
            SpecialistService,
            SpecialtyService,
            BillService
      ]
})
export class FormBillComponent implements OnInit {

      model: any = {};
      dateNow = new Date();

      loadingBranchOffice: boolean;
      loadingSpecialist: boolean;
      loadingSpecialty: boolean;
      loadingReportBySpecialist: boolean;

      branchOffices: Array<any> = new Array<any>();
      specialists: Array<any> = new Array<any>();
      specialtys: Array<any> = new Array<any>();

      constructor(private _branchOfficeService: BranchOfficeService, private _specialistService: SpecialistService, private _specialtyService: SpecialtyService, private _modalService: NgbModal, private router: Router, private _billService: BillService) { }

      ngOnInit() {

            this.branchOfficeFindAll();
            this.valueDefaultModel();
            
      }

      valueDefaultModel(){

            let year: string = this.dateNow.getFullYear().toString();
            let month: string = (this.dateNow.getMonth() + 1).toString();
            let day: string = this.dateNow.getDate().toString();

            month = month.length == 1 ? '0' + month : month;
            
            this.model.dateStart = year + '-' + month + '-' + day;
            this.model.dateEnd = year + '-' + month + '-' + day;

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
                        this.specialistFindAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive();

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

      specialistFindAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive(){

            this.loadingSpecialist = true;
            this._specialistService.findAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive(this.model.specialtyId, this.model.branchOfficeId).subscribe(res => {
                  if(res.statusCode == 200){

                        this.specialists = res.specialist;
                        this.model.specialistId = res.specialist[0].specialistId;
                        this.loadingSpecialist = false;

                  }else if(res.statusCode == 204){

                        this.specialists.length = 0;
                        this.loadingSpecialist = false;

                  }else if(res.statusCode == 500){

                        this.loadingSpecialist = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);
                  }
            })
      }

      billFindBySpecialistIdDateStartAndDateEndAndStatusActiveReport(){

            this.loadingReportBySpecialist = true;
            this._billService.findBySpecialistIdAndDateStartAndDateEndAndStatusActiveReport(this.model.specialistId, this.model.dateStart, this.model.dateEnd).subscribe(res => {
                  if(res.statusCode == 200){

                        let link = document.createElement("a");
                        link.href = "data:application/pdf;base64," + String(res.voucher);
                        link.download = res.voucherName + res.extention;
                        link.dispatchEvent(new MouseEvent("click"));

                        this.loadingReportBySpecialist = false;

                  }else if(res.statusCode == 204){

                        this.loadingReportBySpecialist = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 500){

                        this.loadingReportBySpecialist = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);
                  }
            })

      }

}
