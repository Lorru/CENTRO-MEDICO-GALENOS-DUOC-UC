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
    
    public partial class USER
    {
        public USER()
        {
            this.EVENT_LOG = new HashSet<EVENT_LOG>();
            this.EXCEPTION_LOG = new HashSet<EXCEPTION_LOG>();
        }
    
        public int USER_ID { get; set; }
        public Nullable<int> SPECIALIST_ID { get; set; }
        public Nullable<int> PATIENT_ID { get; set; }
        public Nullable<int> PERSONAL_ID { get; set; }
        public int PROFILE_ID { get; set; }
        public string USER_PASSWORD { get; set; }
        public string USER_STATUS { get; set; }
    
        public virtual ICollection<EVENT_LOG> EVENT_LOG { get; set; }
        public virtual ICollection<EXCEPTION_LOG> EXCEPTION_LOG { get; set; }
        public virtual PATIENT PATIENT { get; set; }
        public virtual PERSONAL PERSONAL { get; set; }
        public virtual SPECIALIST SPECIALIST { get; set; }
    }
}
