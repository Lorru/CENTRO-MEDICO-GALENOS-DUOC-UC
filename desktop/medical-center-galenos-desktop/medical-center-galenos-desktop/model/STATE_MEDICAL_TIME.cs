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
    
    public partial class STATE_MEDICAL_TIME
    {
        public STATE_MEDICAL_TIME()
        {
            this.BILL = new HashSet<BILL>();
        }
    
        public int STATE_MEDICAL_TIME_ID { get; set; }
        public string STATE_MEDICAL_TIME_DESCRIPTION { get; set; }
        public string STATE_MEDICAL_TIME_STATUS { get; set; }
    
        public virtual ICollection<BILL> BILL { get; set; }
    }
}
