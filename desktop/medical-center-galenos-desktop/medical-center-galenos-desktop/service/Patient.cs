using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class Patient
    {
        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<PATIENT> findAllInactive()
        {

            try
            {

                List<PATIENT> patientList = (from p in medicalCenterGalenosEntities.PATIENT
                                           where p.PATIENT_STATUS == "0"
                                           select p).ToList();


                if (patientList.Count > 0)
                {

                    return patientList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "Patient", "findAllInactive");

                return null;
            }

        }
    }
}
