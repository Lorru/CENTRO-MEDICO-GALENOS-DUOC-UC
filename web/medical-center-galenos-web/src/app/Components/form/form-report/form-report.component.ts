import { Component, OnInit } from '@angular/core';
import { BranchOfficeService } from '../../../Services/branchOffice.service';
import { SpecialistService } from '../../../Services/specialist.service';
import { SpecialtyService } from '../../../Services/specialty.service';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalErrorComponent } from '../../modal/modal-error/modal-error.component';
import { BillService } from '../../../Services/bill.service';
// declare let Chart;
declare let Morris;
declare let $;

@Component({
  selector: 'app-form-report',
  templateUrl: './form-report.component.html',
  styleUrls: ['./form-report.component.css'],
  providers: [
            BranchOfficeService,
            SpecialistService,
            SpecialtyService,
            BillService
      ]
})
export class FormReportComponent implements OnInit {

      model: any = {};
      chartFindAll: any = [];
      chartBySpecialistId: any = [];
      dateNow = new Date();

      loadingBranchOffice: boolean;
      loadingReportAll: boolean;
      loadingReportBySpecialist: boolean;
      loadingSpecialist: boolean;
      loadingSpecialty: boolean;

      branchOffices: Array<any> = new Array<any>();
      specialists: Array<any> = new Array<any>();
      specialtys: Array<any> = new Array<any>();
      reportAll: Array<any> = new Array<any>();
      reportBySpecialist: Array<any> = new Array<any>();

      dataFindAll: Array<any> = new Array<any>();
      dataBySpecialist: Array<any> = new Array<any>();
      

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

            this.model.dateStartEspecialist = year + '-' + month + '-' + day;
            this.model.dateEndEspecialist = year + '-' + month + '-' + day;
      }

      getChartFindAll(){

            $("#bar-chart-findAll").remove();
            $('#chart-findAll').append("<div style='height: 250px;' id='bar-chart-findAll'></div>");

            new Morris.Bar({
                  element: 'bar-chart-findAll',
                  data: this.dataFindAll,
                  xkey: 'month',
                  ykeys: ['salary'],
                  labels: ['Valor']
            });


      }

      getChartBySpecialistId(){

            $("#bar-chart-specialistId").remove();
            $('#chart-specialist').append("<div style='height: 250px;' id='bar-chart-specialistId'></div>");

            new Morris.Bar({
                  element: 'bar-chart-specialistId',
                  data: this.dataBySpecialist,
                  xkey: 'month',
                  ykeys: ['salary'],
                  labels: ['Valor']
            });
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

      billFindByDateStartAndDateEndAndStatusActive(){

            this.loadingReportAll = true;
            this.dataFindAll.length = 0;
            this._billService.findByDateStartAndDateEndAndStatusActive(this.model.dateStart, this.model.dateEnd).subscribe(res => {
                  
                  if(res.statusCode == 200){
                        this.reportAll = res.reportAll;

                        if(this.reportAll.length > 0){

                              this.reportAll.forEach(element => {

                                    let reportFindAll : any = {
                                          salary: element[0],
                                          month: element[1]
                                    }


                                    this.dataFindAll.push(reportFindAll);
                              });


                              this.getChartFindAll();

                        }else{

                              let reportFindAll : any = {
                                    salary: 0,
                                    month: ""
                              }

                              this.dataFindAll.push(reportFindAll);

                              this.getChartFindAll();
                        }
                        

                        this.loadingReportAll = false;

                  }else if(res.statusCode == 204){

                        this.loadingReportAll = false;

                  }else if(res.statusCode == 500){

                        this.loadingReportAll = false;

                        const modalRef = this._modalService.open(ModalErrorComponent);
                        modalRef.componentInstance.message = res.message;

                  }else if(res.statusCode == 401){

                        localStorage.clear();
                        this.router.navigate(['/']);
                  }
            })

      }

      billFindBySpecialistIdDateStartAndDateEndAndStatusActive(){

            this.loadingReportBySpecialist = true;
            this.dataBySpecialist.length = 0;
            this._billService.findBySpecialistIdAndDateStartAndDateEndAndStatusActive(this.model.specialistId, this.model.dateStartEspecialist, this.model.dateEndEspecialist).subscribe(res => {
                  if(res.statusCode == 200){

                        this.reportBySpecialist = res.reportBySpecialist;

                        if(this.reportBySpecialist.length > 0){

                              this.reportBySpecialist.forEach(element => {
                                    
                                    let reportSpecialist : any = {
                                          salary: element[0],
                                          month: element[1]
                                    }


                                    this.dataBySpecialist.push(reportSpecialist);

                              });                              

                              this.getChartBySpecialistId();

                        }else{

                              let reportSpecialist : any = {
                                    salary: 0,
                                    month: ""
                              }


                              this.dataBySpecialist.push(reportSpecialist);

                              this.getChartBySpecialistId();
                        }

                        this.loadingReportBySpecialist = false;

                  }else if(res.statusCode == 204){

                        this.loadingReportBySpecialist = false;

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
