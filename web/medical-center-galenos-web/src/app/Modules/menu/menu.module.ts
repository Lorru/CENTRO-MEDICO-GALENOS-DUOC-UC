import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { MenuRoutingModule } from './menu-routing.module';
import { MenuComponent } from './menu.component';
import { HomeComponent } from '../home/home.component';
import { ReportsComponent } from '../reports/reports.component';
import { RegisterMedicalTimeComponent } from '../register-medical-time/register-medical-time.component';
import { FormRegisterMedicalTimeComponent } from '../../Components/form/form-register-medical-time/form-register-medical-time.component';
import { MedicalHoursPaymentComponent } from '../medical-hours-payment/medical-hours-payment.component';
import { TableMedicalHoursPaymentComponent } from '../../Components/table/table-medical-hours-payment/table-medical-hours-payment.component';
import { MyMedicalTimeComponent } from '../my-medical-time/my-medical-time.component';
import { TableMyMedicalTimeComponent } from '../../Components/table/table-my-medical-time/table-my-medical-time.component';
import { SpecialistsComponent } from '../specialists/specialists.component';
import { TableSpececialistComponent } from '../../Components/table/table-spececialist/table-spececialist.component';
import { MyMedicalRecordProfileComponent } from '../my-medical-record-profile/my-medical-record-profile.component';
import { TableMyMedicalRecordProfileComponent } from '../../Components/table/table-my-medical-record-profile/table-my-medical-record-profile.component';
import { MyPatientsComponent } from '../my-patients/my-patients.component';
import { TableMyPatientsComponent } from '../../Components/table/table-my-patients/table-my-patients.component';
import { TablePersonalComponent } from '../../Components/table/table-personal/table-personal.component';
import { PersonalComponent } from '../personal/personal.component';
import { UsersComponent } from '../users/users.component';
import { TableUserComponent } from '../../Components/table/table-user/table-user.component';
import { FormRegisterPatientComponent } from '../../Components/form/form-register-patient/form-register-patient.component';
import { RegisterPatientsComponent } from '../register-patients/register-patients.component';
import { BillsComponent } from '../bills/bills.component';
import { FormBillComponent } from '../../Components/form/form-bill/form-bill.component';
import { FormReportComponent } from '../../Components/form/form-report/form-report.component';
import { LogsComponent } from '../logs/logs.component';
import { TableLogComponent } from '../../Components/table/table-log/table-log.component';
import { NotFoundComponent } from '../not-found/not-found.component';

@NgModule({
  imports: [
    CommonModule,
    MenuRoutingModule,
    FormsModule,
    NgbModule.forRoot()
  ],
  declarations: [
      MenuComponent,
      HomeComponent,
      ReportsComponent,
      RegisterMedicalTimeComponent,
      FormRegisterMedicalTimeComponent,
      MedicalHoursPaymentComponent,
      TableMedicalHoursPaymentComponent,
      MyMedicalTimeComponent,
      TableMyMedicalTimeComponent,
      SpecialistsComponent,
      TableSpececialistComponent,
      MyMedicalRecordProfileComponent,
      TableMyMedicalRecordProfileComponent,
      MyPatientsComponent,
      TableMyPatientsComponent,
      TablePersonalComponent,
      PersonalComponent,
      UsersComponent,
      TableUserComponent,
      RegisterMedicalTimeComponent,
      FormRegisterPatientComponent,
      RegisterPatientsComponent,
      BillsComponent,
      FormBillComponent,
      FormReportComponent,
      LogsComponent,
      TableLogComponent,
      NotFoundComponent
  ],
  exports:[
      MenuComponent,
      HomeComponent,
      ReportsComponent,
      RegisterMedicalTimeComponent,
      FormRegisterMedicalTimeComponent,
      MedicalHoursPaymentComponent,
      TableMedicalHoursPaymentComponent,
      MyMedicalTimeComponent,
      TableMyMedicalTimeComponent,
      SpecialistsComponent,
      TableSpececialistComponent,
      MyMedicalRecordProfileComponent,
      TableMyMedicalRecordProfileComponent,
      MyPatientsComponent,
      TableMyPatientsComponent,
      TablePersonalComponent,
      PersonalComponent,
      UsersComponent,
      TableUserComponent,
      RegisterMedicalTimeComponent,
      FormRegisterPatientComponent,
      RegisterPatientsComponent,
      BillsComponent,
      FormBillComponent,
      FormReportComponent,
      LogsComponent,
      TableLogComponent,
      NotFoundComponent
  ]
})
export class MenuModule { }
