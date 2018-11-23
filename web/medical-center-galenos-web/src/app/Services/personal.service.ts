import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';

@Injectable()
export class PersonalService {

      private headers = new HttpHeaders().set('Content-Type','application/json');
      private URL_API : string = environment.URL_API;
  
      constructor(private http: HttpClient) { }


      findAll(page: number): Observable<any>{

            let URL_PERSONAL = this.URL_API + 'personal/findAll/' + page;
    
            return this.http.get(URL_PERSONAL, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      create(personalRun: string, personalFirstName: string, personalSecondName: string, personalSurName: string, personalSecondSurName: string, personalBirthDate: string, personalEmail: string, userPassword: string, genderId: number, branchOfficeId: number, categoryId: number, profileId: number): Observable<any>{

            let URL_PERSONAL = this.URL_API + 'personal/create';
    
            let user: any = {
                userPassword: userPassword,
                  personalId: {
                        personalRun: personalRun,
                        personalFirstName: personalFirstName,
                        personalSecondName: personalSecondName,
                        personalSurName: personalSurName,
                        personalSecondSurName: personalSecondSurName,
                        personalBirthDate: personalBirthDate,
                        personalEmail: personalEmail,
                        genderId: {
                              genderId: genderId
                        },
                        branchOfficeId: {
                              branchOfficeId: branchOfficeId
                        },
                        categoryId: {
                              categoryId: categoryId
                        }
                  },
                  profileId: {
                        profileId: profileId
                  }

            };
    
            user = JSON.stringify(user);
    
            return this.http.post(URL_PERSONAL, user, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }

      updatePersonalByPersonalId(userId: number, personalId: number, personalRun: string, personalFirstName: string, personalSecondName: string, personalSurName: string, personalSecondSurName: string, personalBirthDate: string, personalEmail: string, userPassword: string, genderId: number, branchOfficeId: number, categoryId: number, profileId: number): Observable<any>{

            let URL_PERSONAL = this.URL_API + 'personal/updatePersonalByPersonalId';
    
            let user: any = {
                  userId: userId,
                  userPassword: userPassword,
                  personalId: {
                        personalId: personalId,
                        personalRun: personalRun,
                        personalFirstName: personalFirstName,
                        personalSecondName: personalSecondName,
                        personalSurName: personalSurName,
                        personalSecondSurName: personalSecondSurName,
                        personalBirthDate: personalBirthDate,
                        personalEmail: personalEmail,
                        genderId: {
                              genderId: genderId
                        },
                        branchOfficeId: {
                              branchOfficeId: branchOfficeId
                        },
                        categoryId: {
                              categoryId: categoryId
                        }
                  },
                  profileId: {
                        profileId: profileId
                  }

            };
    
            user = JSON.stringify(user);
    
            return this.http.put(URL_PERSONAL, user, {headers: this.headers.append('Authorization', localStorage.getItem('token'))});
      }
}
