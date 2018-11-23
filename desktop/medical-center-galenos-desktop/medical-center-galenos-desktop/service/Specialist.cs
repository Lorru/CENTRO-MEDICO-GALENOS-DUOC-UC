using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class Specialist
    {

        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<SPECIALIST> findAllInactive()
        {

            try
            {

                List<SPECIALIST> specialistList = (from s in medicalCenterGalenosEntities.SPECIALIST
                                               where s.SPECIALIST_STATUS == "0"
                                               select s).ToList();


                if (specialistList.Count > 0)
                {

                    return specialistList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "Specialist", "findAllInactive");

                return null;
            }

        }

    }
}
