import { Component, OnInit } from '@angular/core';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { PersonalService } from '../../../Services/personal.service';
import { ModalCreatePersonalComponent } from '../../modal/modal-create-personal/modal-create-personal.component';

@Component({
    selector: 'app-table-personal',
    templateUrl: './table-personal.component.html',
    styleUrls: ['./table-personal.component.css'],
    providers: [
        PersonalService
    ]
})
export class TablePersonalComponent implements OnInit {

    currentPage: number = 1;
    countRowsByPage: number = 5;
    countRows: number;
    loadingPersonals: boolean;

    personals: Array<any> = new Array<any>();

    constructor(private _personalService: PersonalService, private _modalService: NgbModal, private router: Router, private datePipe: DatePipe) { }

    ngOnInit() {

        this.personalFindAll();

    }

    transformPersonalBirthDate(personalBirthDate: string){
        return this.datePipe.transform(personalBirthDate, 'yyyy-MM-dd');
    } 

    personalFindAll(){
        this.currentPage = this.currentPage == 0 ? 1 : this.currentPage;
        this.currentPage = this.currentPage - 1;
        this.loadingPersonals = true;
        this._personalService.findAll(this.currentPage).subscribe(res => {
            if(res.statusCode == 200){

                this.personals = res.personal;
                this.currentPage = res.currentPage;
                this.countRows = res.countRows;
                this.countRowsByPage = res.countRowsByPage;
                this.loadingPersonals = false;

            }else if(res.statusCode == 204){

                this.personals.length = 0;
                this.loadingPersonals = false;


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
        this.loadingPersonals = true;
        this._personalService.findAll(this.currentPage).subscribe(res => {
            if(res.statusCode == 200){

                this.personals = res.personal;
                this.currentPage = res.currentPage + 1;
                this.loadingPersonals = false;

            }else if(res.statusCode == 204){

                this.personals.length = 0;
                this.loadingPersonals = false;


            }else if(res.statusCode == 401){

                localStorage.clear();
                this.router.navigate(['/']);

            }else if(res.statusCode == 500){

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;

            }
        })
    }

    createPersonal(){

        const modalRef = this._modalService.open(ModalCreatePersonalComponent);
        modalRef.result.then((result) => {
            this.currentPage = 1;
            this.countRowsByPage = 5;
            this.personalFindAll();
        }).catch((error) => {
            this.currentPage = 1;
            this.countRowsByPage = 5;
            this.personalFindAll();
        });

    }

}
