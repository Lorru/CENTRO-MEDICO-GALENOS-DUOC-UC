using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class Bill
    {
        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<BILL> findAllInactive()
        {

            try
            {

                List<BILL> billList = (from b in medicalCenterGalenosEntities.BILL
                                                        where b.BILL_STATUS == "0"
                                                        select b).ToList();


                if (billList.Count > 0)
                {

                    return billList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "Bill", "findAllInactive");

                return null;
            }

        }
    }
}
