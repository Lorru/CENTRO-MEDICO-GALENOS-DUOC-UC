import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';

@Injectable()
export class BillService {

      private headers = new HttpHeaders().set('Content-Type','application/json');
      private URL_API : string = environment.URL_API;

      constructor(private http: HttpClient) { }

      create(patientId: number, specialistId: number, scheduleId: number, billMedicalTime: string): Observable<any>{

            let URL_BILL = this.URL_API + 'bill/create';

            let bill: any = {
                  billMedicalTime: billMedicalTime,
                  patientId: {
                        patientId: patientId
                  },
                  specialistId: {
                        specialistId: specialistId
                  },
                  scheduleId: {
                        scheduleId: scheduleId
                  }
            };

            bill = JSON.stringify(bill);

            return this.http.post(URL_BILL, bill, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      findAllByStateMedicalTimeReservedAndPatientRunAndStatusActive(patientRun: string, page: number): Observable<any>{

            let URL_BILL = this.URL_API + 'bill/findAllByStateMedicalTimeReservedAndPatientRunAndStatusActive/' + patientRun + '/' + page;

            return this.http.get(URL_BILL, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      findAllByStateMedicalTimeReservedAndStatusActive(page: number): Observable<any>{

            let URL_BILL = this.URL_API + 'bill/findAllByStateMedicalTimeReservedAndStatusActive/' + page;
    
            return this.http.get(URL_BILL, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      updateBillByBillIdToStateMedicalTimeAndToForecastAndToPaymentTypeAndBillCommissionsAndBillSalary(billId: number, paymentTypeId: number, forecastId: number, billCommissions: number, billSalary: number): Observable<any>{

            let URL_BILL = this.URL_API + 'bill/updateBillByBillIdToStateMedicalTimeAndToForecastAndToPaymentTypeAndBillCommissionsAndBillSalary';
    
            let bill: any = {
                  billId: billId,
                  paymentTypeId: {
                        paymentTypeId: paymentTypeId
                  },
                  forecastId: {
                        forecastId: forecastId
                  },
                  billCommissions: billCommissions,
                  billSalary: billSalary
            };
    
            bill = JSON.stringify(bill);
    
            return this.http.put(URL_BILL, bill, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      findAllByPatientRunAndStatusActive(patientRun: string, page: number): Observable<any>{

            let URL_BILL = this.URL_API + 'bill/findAllByPatientRunAndStatusActive/' + patientRun + '/' + page;

            return this.http.get(URL_BILL, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      updateBillByBillIdToBillStatusInactivo(billId: number): Observable<any>{

            let URL_BILL = this.URL_API + 'bill/updateBillByBillIdToBillStatusInactivo/' + billId;

            return this.http.delete(URL_BILL, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive(specialistId: number, page: number): Observable<any>{

            let URL_BILL = this.URL_API + 'bill/findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive/' + specialistId + '/' + page;

            return this.http.get(URL_BILL, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive(specialistId: number, page: number): Observable<any>{

            let URL_BILL = this.URL_API + 'bill/findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive/' + specialistId + '/' + page;

            return this.http.get(URL_BILL, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      updateBillByBillIdToStateMedicalTimeAttended(billId: number): Observable<any>{
            
            let URL_BILL = this.URL_API + 'bill/updateBillByBillIdToStateMedicalTimeAttended';

            let bill: any = {
                  billId: billId
            };
    
            bill = JSON.stringify(bill);

            return this.http.put(URL_BILL, bill, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      findByDateStartAndDateEndAndStatusActive(dateStart: string, dateEnd: string): Observable<any>{

            let URL_BILL = this.URL_API + 'bill/findByDateStartAndDateEndAndStatusActive/' + dateStart + '/' + dateEnd;

            return this.http.get(URL_BILL, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      findBySpecialistIdAndDateStartAndDateEndAndStatusActive(specialistId: number, dateStart: string, dateEnd: string): Observable<any>{

            let URL_BILL = this.URL_API + 'bill/findBySpecialistIdAndDateStartAndDateEndAndStatusActive/' + specialistId + '/' + dateStart + '/' + dateEnd;

            return this.http.get(URL_BILL, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      findBySpecialistIdAndDateStartAndDateEndAndStatusActiveReport(specialistId: number, dateStart: string, dateEnd: string): Observable<any>{

            let URL_BILL = this.URL_API + 'bill/findBySpecialistIdAndDateStartAndDateEndAndStatusActiveReport/' + specialistId + '/' + dateStart + '/' + dateEnd;

            return this.http.get(URL_BILL, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }
}
