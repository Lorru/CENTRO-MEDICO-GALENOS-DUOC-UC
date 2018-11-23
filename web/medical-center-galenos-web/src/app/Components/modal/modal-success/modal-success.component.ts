import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-modal-success',
    templateUrl: './modal-success.component.html',
    styleUrls: ['./modal-success.component.css']
})
export class ModalSuccessComponent implements OnInit {

    title: string;
    message: string;

    constructor(public activeModal: NgbActiveModal) { }

    ngOnInit() {

    }

    dismiss() {
        this.activeModal.dismiss('Modal Dismiss');
    }
}
