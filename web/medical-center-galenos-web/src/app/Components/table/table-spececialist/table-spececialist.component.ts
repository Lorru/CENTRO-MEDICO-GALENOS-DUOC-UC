import { Component, OnInit } from '@angular/core';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';
import { SpecialistService } from '../../../Services/specialist.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { ModalCreateSpecialistComponent } from '../../modal/modal-create-specialist/modal-create-specialist.component';
import { ModalEnableDisableSpecialistComponent } from '../../modal/modal-enable-disable-specialist/modal-enable-disable-specialist.component';

@Component({
    selector: 'app-table-spececialist',
    templateUrl: './table-spececialist.component.html',
    styleUrls: ['./table-spececialist.component.css'],
    providers: [
        SpecialistService
    ]
})
export class TableSpececialistComponent implements OnInit {

    currentPage: number = 1;
    countRowsByPage: number = 5;
    countRows: number;
    loadingSpecialists: boolean;

    specialists: Array<any> = new Array<any>();

    constructor(private _specialistService: SpecialistService, private _modalService: NgbModal, private router: Router, private datePipe: DatePipe) { }

    ngOnInit() {

        this.specialistFindAll();

    }

    transformSpecialistBirthDate(specialistBirthDate: string){
        return this.datePipe.transform(specialistBirthDate, 'yyyy-MM-dd');
    } 

    returnSpecialistStatus(specialistStatus: number){

        if(specialistStatus == 1){

            return 'HABILITADO';

        }else{

            return 'INHABILITADO';

        }
    }

    specialistFindAll(){
        this.currentPage = this.currentPage == 0 ? 1 : this.currentPage;
        this.currentPage = this.currentPage - 1;
        this.loadingSpecialists = true;
        this._specialistService.findAll(this.currentPage).subscribe(res => {
            if(res.statusCode == 200){

                this.specialists = res.specialist;
                this.currentPage = res.currentPage;
                this.countRows = res.countRows;
                this.countRowsByPage = res.countRowsByPage;
                this.loadingSpecialists = false;

            }else if(res.statusCode == 204){

                this.specialists.length = 0;
                this.loadingSpecialists = false;


            }else if(res.statusCode == 401){

                localStorage.clear();
                this.router.navigate(['/']);

            }else if(res.statusCode == 500){

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;

            }
        })

    }

    pageChange(e){

        this.currentPage = e - 1;
        this.loadingSpecialists = true;
        this._specialistService.findAll(this.currentPage).subscribe(res => {
            if(res.statusCode == 200){

                this.specialists = res.specialist;
                this.currentPage = res.currentPage + 1;
                this.loadingSpecialists = false;

            }else if(res.statusCode == 204){

                this.specialists.length = 0;
                this.loadingSpecialists = false;


            }else if(res.statusCode == 401){

                localStorage.clear();
                this.router.navigate(['/']);

            }else if(res.statusCode == 500){

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;

            }
        })
    }

    createSpecialist(){

        const modalRef = this._modalService.open(ModalCreateSpecialistComponent);
        modalRef.result.then((result) => {
            this.currentPage = 1;
            this.countRowsByPage = 5;
            this.specialistFindAll();
        }).catch((error) => {
            this.currentPage = 1;
            this.countRowsByPage = 5;
            this.specialistFindAll();
        });

    }

    enableDisableSpecialist(specialistId: number){

        const modalRef = this._modalService.open(ModalEnableDisableSpecialistComponent);
        modalRef.componentInstance.objectSpecialist = this.specialists.filter(res => res.specialistId == specialistId);
        modalRef.result.then((result) => {
            this.currentPage = 1;
            this.countRowsByPage = 5;
            this.specialistFindAll();
        }).catch((error) => {
            this.currentPage = 1;
            this.countRowsByPage = 5;
            this.specialistFindAll();
        });

    }

}
