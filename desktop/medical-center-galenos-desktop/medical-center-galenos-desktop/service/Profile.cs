using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class Profile
    {

        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<PROFILE> findAllInactive()
        {

            try
            {

                List<PROFILE> profileList = (from p in medicalCenterGalenosEntities.PROFILE
                                               where p.PROFILE_STATUS == "0"
                                               select p).ToList();


                if (profileList.Count > 0)
                {

                    return profileList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "Profile", "findAllInactive");

                return null;
            }

        }

    }
}
