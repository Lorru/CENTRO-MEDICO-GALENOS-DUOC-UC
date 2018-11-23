using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class Personal
    {
        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<PERSONAL> findAllInactive()
        {

            try
            {

                List<PERSONAL> personalList = (from p in medicalCenterGalenosEntities.PERSONAL
                                           where p.PERSONAL_STATUS == "0"
                                           select p).ToList();


                if (personalList.Count > 0)
                {

                    return personalList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "Personal", "findAllInactive");

                return null;
            }

        }
    }
}
