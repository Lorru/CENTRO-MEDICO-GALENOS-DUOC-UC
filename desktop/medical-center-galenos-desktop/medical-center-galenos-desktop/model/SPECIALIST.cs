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
    
    public partial class SPECIALIST
    {
        public SPECIALIST()
        {
            this.BILL = new HashSet<BILL>();
            this.USER = new HashSet<USER>();
        }
    
        public int SPECIALIST_ID { get; set; }
        public int SPECIALTY_ID { get; set; }
        public int BRANCH_OFFICE_ID { get; set; }
        public int GENDER_ID { get; set; }
        public string SPECIALIST_RUN { get; set; }
        public string SPECIALIST_FIRST_NAME { get; set; }
        public string SPECIALIST_SECOND_NAME { get; set; }
        public string SPECIALIST_SURNAME { get; set; }
        public string SPECIALIST_SECOND_SURNAME { get; set; }
        public string SPECIALIST_FULL_NAME { get; set; }
        public System.DateTime SPECIALIST_BIRTHDATE { get; set; }
        public string SPECIALIST_EMAIL { get; set; }
        public string SPECIALIST_STATUS { get; set; }
    
        public virtual ICollection<BILL> BILL { get; set; }
        public virtual BRANCH_OFFICE BRANCH_OFFICE { get; set; }
        public virtual GENDER GENDER { get; set; }
        public virtual SPECIALTY SPECIALTY { get; set; }
        public virtual ICollection<USER> USER { get; set; }
    }
}