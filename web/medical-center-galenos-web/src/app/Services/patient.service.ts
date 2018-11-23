import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';

@Injectable()
export class PatientService {

    private headers = new HttpHeaders().set('Content-Type','application/json');
    private URL_API : string = environment.URL_API;

    constructor(private http: HttpClient) { }

    create(patientRun: string, patientFirstName: string, patientSecondName: string, patientSurName: string, patientSecondSurName: string, patientBirthDate: string, patientEmail: string, userPassword: string, genderId: number): Observable<any>{

        let URL_PATIENT = this.URL_API + 'patient/create';

        let user: any = {
            userPassword: userPassword,
            patientId: {
                patientRun: patientRun,
                patientFirstName: patientFirstName,
                patientSecondName: patientSecondName,
                patientSurName: patientSurName,
                patientSecondSurName: patientSecondSurName,
                patientBirthDate: patientBirthDate,
                patientEmail: patientEmail,
                genderId: {
                  genderId: genderId
                }
            }
        };

        user = JSON.stringify(user);

        return this.http.post(URL_PATIENT, user, {headers: this.headers});
    }

    findAll(): Observable<any>{

      let URL_PATIENT = this.URL_API + 'patient/findAll';

      return this.http.get(URL_PATIENT, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
  }

  updatePatientByPatientId(userId: number, patientId: number, patientRun: string, patientFirstName: string, patientSecondName: string, patientSurName: string, patientSecondSurName: string, patientBirthDate: string, patientEmail: string, userPassword: string, genderId: number): Observable<any>{

    let URL_PATIENT = this.URL_API + 'patient/updatePatientByPatientId';

    let user: any = {
        userId: userId,
        userPassword: userPassword,
        patientId: {
            patientId: patientId,
            patientRun: patientRun,
            patientFirstName: patientFirstName,
            patientSecondName: patientSecondName,
            patientSurName: patientSurName,
            patientSecondSurName: patientSecondSurName,
            patientBirthDate: patientBirthDate,
            patientEmail: patientEmail,
            genderId: {
              genderId: genderId
            }
        }
    };

    user = JSON.stringify(user);

    return this.http.put(URL_PATIENT, user, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
}

}
