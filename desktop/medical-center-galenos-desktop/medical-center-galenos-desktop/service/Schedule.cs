using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class Schedule
    {

        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<SCHEDULE> findAllInactive()
        {

            try
            {

                List<SCHEDULE> scheduleList = (from s in medicalCenterGalenosEntities.SCHEDULE
                                       where s.SCHEDULE_STATUS == "0"
                                       select s).ToList();


                if (scheduleList.Count > 0)
                {

                    return scheduleList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "Schedule", "findAllInactive");

                return null;
            }

        }

    }
}
