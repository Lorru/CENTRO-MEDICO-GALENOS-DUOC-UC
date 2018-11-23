using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class PaymentType
    {
        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<PAYMENT_TYPE> findAllInactive()
        {

            try
            {

                List<PAYMENT_TYPE> paymentTypeList = (from p in medicalCenterGalenosEntities.PAYMENT_TYPE
                                           where p.PAYMENT_TYPE_STATUS == "0"
                                           select p).ToList();


                if (paymentTypeList.Count > 0)
                {

                    return paymentTypeList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "PaymentType", "findAllInactive");

                return null;
            }

        }
    }
}
