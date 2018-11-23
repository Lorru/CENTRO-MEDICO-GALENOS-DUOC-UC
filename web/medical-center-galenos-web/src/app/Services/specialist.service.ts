import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';

@Injectable()
export class SpecialistService {

      private headers = new HttpHeaders().set('Content-Type','application/json');
      private URL_API : string = environment.URL_API;
  
      constructor(private http: HttpClient) { }

      findAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive(specialtyId: number, branchOfficeId: number): Observable<any>{
  
            let URL_SPECIALIST = this.URL_API + 'specialist/findAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive/' + specialtyId + '/' + branchOfficeId;
    
            return this.http.get(URL_SPECIALIST, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      create(specialistRun: string, specialistFirstName: string, specialistSecondName: string, specialistSurName: string, specialistSecondSurName: string, specialistBirthDate: string, specialistEmail: string, userPassword: string, genderId: number, branchOfficeId: number, specialtyId: number): Observable<any>{

            let URL_SPECIALIST = this.URL_API + 'specialist/create';
    
            let user: any = {
                userPassword: userPassword,
                specialistId: {
                        specialistRun: specialistRun,
                        specialistFirstName: specialistFirstName,
                        specialistSecondName: specialistSecondName,
                        specialistSurName: specialistSurName,
                        specialistSecondSurName: specialistSecondSurName,
                        specialistBirthDate: specialistBirthDate,
                        specialistEmail: specialistEmail,
                        genderId: {
                              genderId: genderId
                        },
                        branchOfficeId: {
                              branchOfficeId: branchOfficeId
                        },
                        specialtyId: {
                              specialtyId: specialtyId
                        }
                  }
            };
    
            user = JSON.stringify(user);
    
            return this.http.post(URL_SPECIALIST, user, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      findAll(page: number): Observable<any>{

            let URL_SPECIALIST = this.URL_API + 'specialist/findAll/' + page;
    
            return this.http.get(URL_SPECIALIST, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      updateSpecialistBySpecialistIdAndSpecialistStatus(specialistId: number, specialistStatus: string): Observable<any>{

            let URL_SPECIALIST = this.URL_API + 'specialist/updateSpecialistBySpecialistIdAndSpecialistStatus';
    
            let specialist: any = {
                specialistId: specialistId,
                specialistStatus: specialistStatus
            };
    
            specialist = JSON.stringify(specialist);
    
            return this.http.put(URL_SPECIALIST, specialist, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      updateSpecialistBySpecialistId(userId: number, specialistId: number, specialistRun: string, specialistFirstName: string, specialistSecondName: string, specialistSurName: string, specialistSecondSurName: string, specialistBirthDate: string, specialistEmail: string, userPassword: string, genderId: number, branchOfficeId: number, specialtyId: number): Observable<any>{

            let URL_SPECIALIST = this.URL_API + 'specialist/updateSpecialistBySpecialistId';
    
            let user: any = {
                  userId: userId,
                  userPassword: userPassword,
                  specialistId: {
                              specialistId: specialistId,
                              specialistRun: specialistRun,
                              specialistFirstName: specialistFirstName,
                              specialistSecondName: specialistSecondName,
                              specialistSurName: specialistSurName,
                              specialistSecondSurName: specialistSecondSurName,
                              specialistBirthDate: specialistBirthDate,
                              specialistEmail: specialistEmail,
                              genderId: {
                                    genderId: genderId
                              },
                              branchOfficeId: {
                                    branchOfficeId: branchOfficeId
                              },
                              specialtyId: {
                                    specialtyId: specialtyId
                              }
                        }
            };
    
            user = JSON.stringify(user);
    
            return this.http.put(URL_SPECIALIST, user, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

}
