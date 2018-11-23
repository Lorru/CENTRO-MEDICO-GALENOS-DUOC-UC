using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class BranchOffice
    {
        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<BRANCH_OFFICE> findAllInactive()
        {

            try
            {

                List<BRANCH_OFFICE> branchOfficeList = (from b in medicalCenterGalenosEntities.BRANCH_OFFICE
                                       where b.BRANCH_OFFICE_STATUS == "0"
                                       select b).ToList();


                if (branchOfficeList.Count > 0)
                {

                    return branchOfficeList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "BranchOffice", "findAllInactive");

                return null;
            }

        }
    }
}
