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
    
    public partial class PATIENT
    {
        public PATIENT()
        {
            this.BILL = new HashSet<BILL>();
            this.USER = new HashSet<USER>();
        }
    
        public int PATIENT_ID { get; set; }
        public int GENDER_ID { get; set; }
        public string PATIENT_RUN { get; set; }
        public string PATIENT_FIRST_NAME { get; set; }
        public string PATIENT_SECOND_NAME { get; set; }
        public string PATIENT_SURNAME { get; set; }
        public string PATIENT_SECOND_SURNAME { get; set; }
        public string PATIENT_FULL_NAME { get; set; }
        public System.DateTime PATIENT_BIRTHDATE { get; set; }
        public string PATIENT_EMAIL { get; set; }
        public string PATIENT_STATUS { get; set; }
    
        public virtual ICollection<BILL> BILL { get; set; }
        public virtual GENDER GENDER { get; set; }
        public virtual ICollection<USER> USER { get; set; }
    }
}
