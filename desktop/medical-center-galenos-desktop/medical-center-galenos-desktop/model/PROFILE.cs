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
    
    public partial class PROFILE
    {
        public PROFILE()
        {
            this.PROFILE_ROLE = new HashSet<PROFILE_ROLE>();
        }
    
        public int PROFILE_ID { get; set; }
        public string PROFILE_DESCRIPTION { get; set; }
        public string PROFILE_STATUS { get; set; }
    
        public virtual ICollection<PROFILE_ROLE> PROFILE_ROLE { get; set; }
    }
}