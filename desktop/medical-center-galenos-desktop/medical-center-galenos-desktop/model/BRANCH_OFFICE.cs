//------------------------------------------------------------------------------
// <auto-generated>
//    Este código se generó a partir de una plantilla.
//
//    Los cambios manuales en este archivo pueden causar un comportamiento inesperado de la aplicación.
//    Los cambios manuales en este archivo se sobrescribirán si se regenera el código.
// </auto-generated>
//------------------------------------------------------------------------------

namespace medical_center_galenos_desktop.model
{
    using System;
    using System.Collections.Generic;
    
    public partial class BRANCH_OFFICE
    {
        public BRANCH_OFFICE()
        {
            this.BRANCH_OFFICE_CATEGORY = new HashSet<BRANCH_OFFICE_CATEGORY>();
            this.BRANCH_OFFICE_SPECIALTY = new HashSet<BRANCH_OFFICE_SPECIALTY>();
            this.PERSONAL = new HashSet<PERSONAL>();
            this.SPECIALIST = new HashSet<SPECIALIST>();
        }
    
        public int BRANCH_OFFICE_ID { get; set; }
        public string BRANCH_OFFICE_NAME { get; set; }
        public string BRANCH_OFFICE_LOCATION { get; set; }
        public string BRANCH_OFFICE_STATUS { get; set; }
    
        public virtual ICollection<BRANCH_OFFICE_CATEGORY> BRANCH_OFFICE_CATEGORY { get; set; }
        public virtual ICollection<BRANCH_OFFICE_SPECIALTY> BRANCH_OFFICE_SPECIALTY { get; set; }
        public virtual ICollection<PERSONAL> PERSONAL { get; set; }
        public virtual ICollection<SPECIALIST> SPECIALIST { get; set; }
    }
}
