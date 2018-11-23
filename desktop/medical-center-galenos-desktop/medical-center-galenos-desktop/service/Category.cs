using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using medical_center_galenos_desktop.model;

namespace medical_center_galenos_desktop.service
{
    public class Category
    {
        private MedicalCenterGalenosEntities medicalCenterGalenosEntities = new MedicalCenterGalenosEntities();

        public List<CATEGORY> findAllInactive()
        {

            try
            {

                List<CATEGORY> categoryList = (from c in medicalCenterGalenosEntities.CATEGORY
                                       where c.CATEGORY_STATUS == "0"
                                       select c).ToList();


                if (categoryList.Count > 0)
                {

                    return categoryList;

                }
                else
                {

                    return null;

                }

            }
            catch (Exception ex)
            {

                medicalCenterGalenosEntities.EXCEPTION_LOG_SP(ex.Message, DateTime.Now, 500, "Category", "findAllInactive");

                return null;
            }

        }
    }
}
