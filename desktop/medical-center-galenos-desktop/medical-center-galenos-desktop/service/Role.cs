using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class Role
    {

        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<ROLE> findAllInactive()
        {

            try
            {

                List<ROLE> roleList = (from r in medicalCenterGalenosEntities.ROLE
                                             where r.ROLE_STATUS == "0"
                                             select r).ToList();


                if (roleList.Count > 0)
                {

                    return roleList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "Role", "findAllInactive");

                return null;
            }

        }

    }
}
