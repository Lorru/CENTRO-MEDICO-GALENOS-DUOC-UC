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
    
    public partial class PAYMENT_TYPE
    {
        public PAYMENT_TYPE()
        {
            this.BILL = new HashSet<BILL>();
        }
    
        public int PAYMENT_TYPE_ID { get; set; }
        public string PAYMENT_TYPE_DESCRIPTION { get; set; }
        public int PAYMENT_TYPE_AMOUNT { get; set; }
        public string PAYMENT_TYPE_STATUS { get; set; }
    
        public virtual ICollection<BILL> BILL { get; set; }
    }
}