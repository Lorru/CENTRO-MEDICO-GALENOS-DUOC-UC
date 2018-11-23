using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class StateMedicalTime
    {

        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<STATE_MEDICAL_TIME> findAllInactive()
        {

            try
            {

                List<STATE_MEDICAL_TIME> stateMedicalTimeList = (from s in medicalCenterGalenosEntities.STATE_MEDICAL_TIME
                                                 where s.STATE_MEDICAL_TIME_STATUS == "0"
                                                 select s).ToList();


                if (stateMedicalTimeList.Count > 0)
                {

                    return stateMedicalTimeList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "StateMedicalTime", "findAllInactive");

                return null;
            }

        }

    }
}
