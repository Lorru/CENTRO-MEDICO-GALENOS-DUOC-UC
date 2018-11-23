using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class Gender
    {
        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<GENDER> findAllInactive()
        {

            try
            {

                List<GENDER> genderList = (from g in medicalCenterGalenosEntities.GENDER
                                               where g.GENDER_STATUS == "0"
                                               select g).ToList();


                if (genderList.Count > 0)
                {

                    return genderList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "Gender", "findAllInactive");

                return null;
            }

        }
    }
}
