using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class Specialty
    {

        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<SPECIALTY> findAllInactive()
        {

            try
            {

                List<SPECIALTY> specialtyList = (from s in medicalCenterGalenosEntities.SPECIALTY
                                                   where s.SPECIALTY_STATUS == "0"
                                                   select s).ToList();


                if (specialtyList.Count > 0)
                {

                    return specialtyList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "Specialty", "findAllInactive");

                return null;
            }

        }

    }
}
