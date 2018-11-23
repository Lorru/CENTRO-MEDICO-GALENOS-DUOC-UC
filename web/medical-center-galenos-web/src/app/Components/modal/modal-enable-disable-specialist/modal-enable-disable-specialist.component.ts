import { Component, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SpecialistService } from '../../../Services/specialist.service';
import { ModalSuccessComponent } from '../modal-success/modal-success.component';
import { ModalErrorComponent } from '../modal-error/modal-error.component';
import { Router } from '@angular/router';

@Component({
    selector: 'app-modal-enable-disable-specialist',
    templateUrl: './modal-enable-disable-specialist.component.html',
    styleUrls: ['./modal-enable-disable-specialist.component.css'],
    providers:[
        SpecialistService
    ]
})
export class ModalEnableDisableSpecialistComponent implements OnInit {

    specialistStatus: number;
    title: string;
    titleButton: string;
    message: string;
    loadingSpecialistStatus: boolean;
    objectSpecialist: any;

    constructor(public activeModal: NgbActiveModal, private _specialistService: SpecialistService, private _modalService: NgbModal, private router: Router) { }

    ngOnInit() {

        this.getMessages();
        
    }

    getMessages(){

        this.specialistStatus = this.objectSpecialist[0].specialistStatus;
        if(this.specialistStatus == 1){

            this.title = "Deshabilitar Especialista.";
            this.titleButton = "Deshabilitar";
            this.message = "¿Seguro que quiere deshabilitar al especialista " + this.objectSpecialist[0].specialistFullName + ' ?.';

        }else{

            this.title = "Habilitar Especialista.";
            this.titleButton = "Habilitar";
            this.message = "¿Seguro que quiere habilitar al especialista " + this.objectSpecialist[0].specialistFullName + ' ?.';

        }

    }

    dismiss() {
        this.activeModal.dismiss('Modal Dismiss');
    }

    enableDisabledSpecialist(){
        this.loadingSpecialistStatus = true;
        this.specialistStatus = this.specialistStatus == 1 ? 0 : 1;
        this._specialistService.updateSpecialistBySpecialistIdAndSpecialistStatus(this.objectSpecialist[0].specialistId, this.specialistStatus.toString()).subscribe(res => {

            if(res.statusCode == 200){

                this.loadingSpecialistStatus = false;

                const modalRef = this._modalService.open(ModalSuccessComponent);
                modalRef.componentInstance.message = this.specialistStatus == 1 ? 'Se habilito el especialista ' + this.objectSpecialist[0].specialistFullName + ' correctamente.' : 'Se deshabilito el especialista ' + this.objectSpecialist[0].specialistFullName + ' correctamente.';
                modalRef.componentInstance.title = this.specialistStatus == 1 ? 'Habilitar especialista.' : 'Deshabilitar especialista.';
                modalRef.result.then((result) => {
                    this.dismiss();
                }).catch((error) => {
                    this.dismiss();
                });

            }else if(res.statusCode == 204){

                this.loadingSpecialistStatus = false;

                const modalRef = this._modalService.open(ModalErrorComponent);
                modalRef.componentInstance.message = res.message;

            }else if(res.statusCode == 500){

                this.loadingSpecialistStatus = false;

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
