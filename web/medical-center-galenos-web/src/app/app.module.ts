import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { LoginComponent } from './Modules/login/login.component';
import { RegisterComponent } from './Modules/register/register.component';
import { AppRoutingModule } from './app-routing.module';
import { MenuModule } from './Modules/menu/menu.module';
import { ModalErrorComponent } from './Components/modal/modal-error/modal-error.component';
import { FormRegisterComponent } from './Components/form/form-register/form-register.component';
import { FormLoginComponent } from './Components/form/form-login/form-login.component';
import { ModalSuccessComponent } from './Components/modal/modal-success/modal-success.component';
import { ModalConfirmationMedicalTimeComponent } from './Components/modal/modal-confirmation-medical-time/modal-confirmation-medical-time.component';
import { ModalPayMedicalTimeComponent } from './Components/modal/modal-pay-medical-time/modal-pay-medical-time.component';
import { ModalCancelMedicalTimeComponent } from './Components/modal/modal-cancel-medical-time/modal-cancel-medical-time.component';
import { ModalCreateSpecialistComponent } from './Components/modal/modal-create-specialist/modal-create-specialist.component';
import { ModalEnableDisableSpecialistComponent } from './Components/modal/modal-enable-disable-specialist/modal-enable-disable-specialist.component';
import { ModalEnableDisableMyProfileComponent } from './Components/modal/modal-enable-disable-my-profile/modal-enable-disable-my-profile.component';
import { ModalAttendedPatientComponent } from './Components/modal/modal-attended-patient/modal-attended-patient.component';
import { ModalCreatePersonalComponent } from './Components/modal/modal-create-personal/modal-create-personal.component';
import { ModalEditPatientComponent } from './Components/modal/modal-edit-patient/modal-edit-patient.component';
import { ModalEditSpecialistComponent } from './Components/modal/modal-edit-specialist/modal-edit-specialist.component';
import { ModalEditPersonalComponent } from './Components/modal/modal-edit-personal/modal-edit-personal.component';
import { ModalEnableDisableUserComponent } from './Components/modal/modal-enable-disable-user/modal-enable-disable-user.component';

@NgModule({
  declarations: [
            AppComponent,
            LoginComponent,
            RegisterComponent,
            ModalErrorComponent,
            FormRegisterComponent,
            FormLoginComponent,
            ModalSuccessComponent,
            ModalConfirmationMedicalTimeComponent,
            ModalPayMedicalTimeComponent,
            ModalCancelMedicalTimeComponent,
            ModalCreateSpecialistComponent,
            ModalEnableDisableSpecialistComponent,
            ModalEnableDisableMyProfileComponent,
            ModalAttendedPatientComponent,
            ModalCreatePersonalComponent,
            ModalEditPatientComponent,
            ModalEditSpecialistComponent,
            ModalEditPersonalComponent,
            ModalEnableDisableUserComponent
      ],
  imports: [
            BrowserModule,
            AppRoutingModule,
            MenuModule,
            HttpClientModule,
            FormsModule,
            NgbModule.forRoot()
      ],
  providers: [

      ],
  bootstrap: [
      AppComponent
      ],
  entryComponents: [
            ModalErrorComponent,
            ModalSuccessComponent,
            ModalConfirmationMedicalTimeComponent,
            ModalPayMedicalTimeComponent,
            ModalCancelMedicalTimeComponent,
            ModalCreateSpecialistComponent,
            ModalEnableDisableSpecialistComponent,
            ModalEnableDisableMyProfileComponent,
            ModalAttendedPatientComponent,
            ModalCreatePersonalComponent,
            ModalEditPatientComponent,
            ModalEditSpecialistComponent,
            ModalEditPersonalComponent,
            ModalEnableDisableUserComponent
      ]
})
export class AppModule { }
