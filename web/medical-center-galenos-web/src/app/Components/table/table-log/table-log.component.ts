import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { EventLogService } from '../../../Services/eventLog.service';
import { ExceptionLogService } from '../../../Services/exceptionLog.service';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-table-log',
  templateUrl: './table-log.component.html',
  styleUrls: ['./table-log.component.css'],
  providers:[
      EventLogService,
      ExceptionLogService
  ]
})
export class TableLogComponent implements OnInit {

      currentPageEventLog: number = 1;
      countRowsByPageEventLog: number = 5;
      countRowsEventLog: number;

      currentPageExceptionLog: number = 1;
      countRowsByPageExceptionLog: number = 5;
      countRowsExceptionLog: number;

      loadingEventLog: boolean;
      loadingExceptionLog: boolean;

      eventsLog: Array<any> = new Array<any>();
      exceptionsLog: Array<any> = new Array<any>();

      constructor(private datePipe: DatePipe, private _eventLogService: EventLogService, private _exceptionLogService: ExceptionLogService, private _modalService: NgbModal, private router: Router) { }

      ngOnInit() {
          this.eventLogFindAll();
          this.exceptionLogFindAll();
      }

      returnEventLogUser(userId: any){

            if(userId.profileId.profileId == 2){

                  return userId.specialistId.specialistFullName;

            }else if(userId.profileId.profileId == 5){

                  return userId.patientId.patientFullName;

            }else{

                  return userId.personalId.personalFullName;

            }

      }

      returnExceptionLogUser(userId: any){

            if(userId != null){

                  if(userId.profileId.profileId == 2){

                        return userId.specialistId.specialistFullName;
      
                  }else if(userId.profileId.profileId == 5){
      
                        return userId.patientId.patientFullName;
      
                  }else{
      
                        return userId.personalId.personalFullName;
      
                  }

            }else{

                  return "No contiene usuario.";

            }

      }

      transformEventLogDate(eventLogDate: string){
          return this.datePipe.transform(eventLogDate, 'yyyy-MM-dd HH:mm:ss');
      }

      transformExceptionLogDate(exceptionLogDate: string){
          return this.datePipe.transform(exceptionLogDate, 'yyyy-MM-dd HH:mm:ss');
      }

      eventLogFindAll(){

        this.currentPageEventLog = this.currentPageEventLog - 1;
        this.loadingEventLog = true;
        this._eventLogService.findAll(this.currentPageEventLog).subscribe(res => {
              if(res.statusCode == 200){

                    this.eventsLog = res.eventLog;
                    this.currentPageEventLog = res.currentPage;
                    this.countRowsEventLog = res.countRows;
                    this.countRowsByPageEventLog = res.countRowsByPage;
                    this.loadingEventLog = false;

              }else if(res.statusCode == 204){

                    this.eventsLog.length = 0;
                    this.loadingEventLog = false;

              }else if(res.statusCode == 401){

                    localStorage.clear();
                    this.router.navigate(['/']);

              }else if(res.statusCode == 500){

                    const modalRef = this._modalService.open(ModalErrorComponent);
                    modalRef.componentInstance.message = res.message;

              }
        })
      }

      exceptionLogFindAll(){

        this.currentPageExceptionLog = this.currentPageExceptionLog - 1;
        this.loadingExceptionLog = true;
        this._exceptionLogService.findAll(this.currentPageExceptionLog).subscribe(res => {
              if(res.statusCode == 200){

                    this.exceptionsLog = res.exceptionLog;
                    this.currentPageExceptionLog = res.currentPage;
                    this.countRowsExceptionLog = res.countRows;
                    this.countRowsByPageExceptionLog = res.countRowsByPage;
                    this.loadingExceptionLog = false;

              }else if(res.statusCode == 204){

                    this.exceptionsLog.length = 0;
                    this.loadingExceptionLog = false;

              }else if(res.statusCode == 401){

                    localStorage.clear();
                    this.router.navigate(['/']);

              }else if(res.statusCode == 500){

                    const modalRef = this._modalService.open(ModalErrorComponent);
                    modalRef.componentInstance.message = res.message;

              }
        })
      }

      pageChangeEventLog(e){

        this.currentPageEventLog = e - 1;
        this.loadingEventLog = true;
        this._eventLogService.findAll(this.currentPageEventLog).subscribe(res => {
              if(res.statusCode == 200){

                    this.eventsLog = res.eventLog;
                    this.currentPageEventLog = res.currentPage + 1;
                    this.loadingEventLog = false;

              }else if(res.statusCode == 204){

                    this.eventsLog.length = 0;
                    this.loadingEventLog = false;


              }else if(res.statusCode == 401){

                    localStorage.clear();
                    this.router.navigate(['/']);

              }else if(res.statusCode == 500){

                    const modalRef = this._modalService.open(ModalErrorComponent);
                    modalRef.componentInstance.message = res.message;

              }
        })
      }

      pageChangeExceptionLog(e){

        this.currentPageExceptionLog = e - 1;
        this.loadingExceptionLog = true;
        this._exceptionLogService.findAll(this.currentPageExceptionLog).subscribe(res => {
              if(res.statusCode == 200){

                    this.exceptionsLog = res.exceptionLog;
                    this.currentPageExceptionLog = res.currentPage + 1;
                    this.loadingExceptionLog = false;

              }else if(res.statusCode == 204){

                    this.exceptionsLog.length = 0;
                    this.loadingExceptionLog = false;


              }else if(res.statusCode == 401){

                    localStorage.clear();
                    this.router.navigate(['/']);

              }else if(res.statusCode == 500){

                    const modalRef = this._modalService.open(ModalErrorComponent);
                    modalRef.componentInstance.message = res.message;

              }
        })
      }
}
