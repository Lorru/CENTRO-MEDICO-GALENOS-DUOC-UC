import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MenuComponent } from './menu.component';
import { HomeComponent } from '../home/home.component';
import { RegisterMedicalTimeComponent } from '../register-medical-time/register-medical-time.component';
import { MedicalHoursPaymentComponent } from '../medical-hours-payment/medical-hours-payment.component';
import { MyMedicalTimeComponent } from '../my-medical-time/my-medical-time.component';
import { SpecialistsComponent } from '../specialists/specialists.component';
import { MyMedicalRecordProfileComponent } from '../my-medical-record-profile/my-medical-record-profile.component';
import { MyPatientsComponent } from '../my-patients/my-patients.component';
import { PersonalComponent } from '../personal/personal.component';
import { UsersComponent } from '../users/users.component';
import { RegisterPatientsComponent } from '../register-patients/register-patients.component';
import { BillsComponent } from '../bills/bills.component';
import { ReportsComponent } from '../reports/reports.component';
import { LogsComponent } from '../logs/logs.component';
import { NotFoundComponent } from '../not-found/not-found.component';

const routes: Routes = [
      {     path: 'menu',     component:  MenuComponent,    children:   [
            {     path: 'home',     component:  HomeComponent},
            {     path: 'register-medical-time',      component:  RegisterMedicalTimeComponent},
            {     path: 'medical-hours-payment',      component:  MedicalHoursPaymentComponent},
            {     path: 'my-medical-time',      component:  MyMedicalTimeComponent},
            {     path: 'specialists',      component:  SpecialistsComponent},
            {     path: 'my-medical-record-profile',      component:  MyMedicalRecordProfileComponent},
            {     path: 'my-patients',      component:  MyPatientsComponent},
            {     path: 'personals',      component:  PersonalComponent},
            {     path: 'users',      component:  UsersComponent},
            {     path: 'register-patients',      component:  RegisterPatientsComponent},
            {     path: 'bills',      component:  BillsComponent},
            {     path: 'reports',      component:  ReportsComponent},
            {     path: 'logs',      component:  LogsComponent},
            {     path: '**',      component:  NotFoundComponent},
      ]}
];

@NgModule({
      imports: [RouterModule.forChild(routes)],
      exports: [RouterModule]
})
export class MenuRoutingModule { }
