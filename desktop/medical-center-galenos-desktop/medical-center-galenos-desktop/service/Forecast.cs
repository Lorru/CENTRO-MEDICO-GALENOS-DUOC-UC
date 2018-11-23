using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class Forecast
    {

        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<FORECAST> findAllInactive()
        {

            try
            {

                List<FORECAST> forecastList = (from f in medicalCenterGalenosEntities.FORECAST
                                                        where f.FORECAST_STATUS == "0"
                                                        select f).ToList();


                if (forecastList.Count > 0)
                {

                    return forecastList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "Forecast", "findAllInactive");

                return null;
            }

        }
    }
}
