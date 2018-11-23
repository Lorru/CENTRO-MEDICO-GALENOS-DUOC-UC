using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class User
    {
        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public USER login(string personalEmail, string userPassword)
        {
            try
            {

                USER userExist = (from u in medicalCenterGalenosEntities.USER
                                  join p in medicalCenterGalenosEntities.PERSONAL on u.PERSONAL_ID equals p.PERSONAL_ID
                                  where p.PERSONAL_EMAIL == personalEmail && u.USER_STATUS == "1" && u.PROFILE_ID == 1
                                  select u).FirstOrDefault();


                if(userExist == null)
                {

                    return null;

                }
                else
                {

                    bool validateUser = BCrypt.Net.BCrypt.Verify(userPassword, userExist.USER_PASSWORD);

                    if (validateUser == true)
                    {

                        medicalCenterGalenosEntities.EVENT_LOG_SP(userExist.USER_ID, "The user successfully logged in", DateTime.Now, 200);

                        return userExist;


                    }
                    else
                    {

                        return null;

                    }

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "User", "Login");

                return null;
            }
        }
    }
}
