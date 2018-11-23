import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
      selector: 'app-modal-error',
      templateUrl: './modal-error.component.html',
      styleUrls: ['./modal-error.component.css']
})
export class ModalErrorComponent implements OnInit {

      message : string;

      constructor(public activeModal: NgbActiveModal) { }

      ngOnInit() {
      }

      dismiss() {
            this.activeModal.dismiss('Modal Dismiss');
      }
}
