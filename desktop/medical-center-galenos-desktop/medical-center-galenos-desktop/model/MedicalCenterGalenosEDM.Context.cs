﻿//------------------------------------------------------------------------------
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
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    using System.Data.Objects;
    using System.Data.Objects.DataClasses;
    using System.Linq;
    
    public partial class MedicalCenterGalenosEntities : DbContext
    {
        public MedicalCenterGalenosEntities()
            : base("name=MedicalCenterGalenosEntities")
        {
        }
    
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            throw new UnintentionalCodeFirstException();
        }
    
        public DbSet<BILL> BILL { get; set; }
        public DbSet<BRANCH_OFFICE> BRANCH_OFFICE { get; set; }
        public DbSet<BRANCH_OFFICE_CATEGORY> BRANCH_OFFICE_CATEGORY { get; set; }
        public DbSet<BRANCH_OFFICE_SPECIALTY> BRANCH_OFFICE_SPECIALTY { get; set; }
        public DbSet<CATEGORY> CATEGORY { get; set; }
        public DbSet<EVENT_LOG> EVENT_LOG { get; set; }
        public DbSet<EXCEPTION_LOG> EXCEPTION_LOG { get; set; }
        public DbSet<FORECAST> FORECAST { get; set; }
        public DbSet<GENDER> GENDER { get; set; }
        public DbSet<PATIENT> PATIENT { get; set; }
        public DbSet<PAYMENT_TYPE> PAYMENT_TYPE { get; set; }
        public DbSet<PERSONAL> PERSONAL { get; set; }
        public DbSet<PROFILE> PROFILE { get; set; }
        public DbSet<PROFILE_ROLE> PROFILE_ROLE { get; set; }
        public DbSet<ROLE> ROLE { get; set; }
        public DbSet<SCHEDULE> SCHEDULE { get; set; }
        public DbSet<SPECIALIST> SPECIALIST { get; set; }
        public DbSet<SPECIALTY> SPECIALTY { get; set; }
        public DbSet<STATE_MEDICAL_TIME> STATE_MEDICAL_TIME { get; set; }
        public DbSet<USER> USER { get; set; }
    
        public virtual int EVENT_LOG_SP(Nullable<decimal> uSERID, string eVENTLOGDESCRIPTION, Nullable<System.DateTime> eVENTLOGDATE, Nullable<decimal> eVENTLOGSTATUSCODE)
        {
            var uSERIDParameter = uSERID.HasValue ?
                new ObjectParameter("USERID", uSERID) :
                new ObjectParameter("USERID", typeof(decimal));
    
            var eVENTLOGDESCRIPTIONParameter = eVENTLOGDESCRIPTION != null ?
                new ObjectParameter("EVENTLOGDESCRIPTION", eVENTLOGDESCRIPTION) :
                new ObjectParameter("EVENTLOGDESCRIPTION", typeof(string));
    
            var eVENTLOGDATEParameter = eVENTLOGDATE.HasValue ?
                new ObjectParameter("EVENTLOGDATE", eVENTLOGDATE) :
                new ObjectParameter("EVENTLOGDATE", typeof(System.DateTime));
    
            var eVENTLOGSTATUSCODEParameter = eVENTLOGSTATUSCODE.HasValue ?
                new ObjectParameter("EVENTLOGSTATUSCODE", eVENTLOGSTATUSCODE) :
                new ObjectParameter("EVENTLOGSTATUSCODE", typeof(decimal));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction("EVENT_LOG_SP", uSERIDParameter, eVENTLOGDESCRIPTIONParameter, eVENTLOGDATEParameter, eVENTLOGSTATUSCODEParameter);
        }
    
        public virtual int EXCEPTION_LOG_SP(string eXCEPTIONLOGDESCRIPTION, Nullable<System.DateTime> eXCEPTIONLOGDATE, Nullable<decimal> eXCEPTIONLOGSTATUSCODE, string eXCEPTIONLOGCONTROLLER, string eXCEPTIONLOGMETHOD)
        {
            var eXCEPTIONLOGDESCRIPTIONParameter = eXCEPTIONLOGDESCRIPTION != null ?
                new ObjectParameter("EXCEPTIONLOGDESCRIPTION", eXCEPTIONLOGDESCRIPTION) :
                new ObjectParameter("EXCEPTIONLOGDESCRIPTION", typeof(string));
    
            var eXCEPTIONLOGDATEParameter = eXCEPTIONLOGDATE.HasValue ?
                new ObjectParameter("EXCEPTIONLOGDATE", eXCEPTIONLOGDATE) :
                new ObjectParameter("EXCEPTIONLOGDATE", typeof(System.DateTime));
    
            var eXCEPTIONLOGSTATUSCODEParameter = eXCEPTIONLOGSTATUSCODE.HasValue ?
                new ObjectParameter("EXCEPTIONLOGSTATUSCODE", eXCEPTIONLOGSTATUSCODE) :
                new ObjectParameter("EXCEPTIONLOGSTATUSCODE", typeof(decimal));
    
            var eXCEPTIONLOGCONTROLLERParameter = eXCEPTIONLOGCONTROLLER != null ?
                new ObjectParameter("EXCEPTIONLOGCONTROLLER", eXCEPTIONLOGCONTROLLER) :
                new ObjectParameter("EXCEPTIONLOGCONTROLLER", typeof(string));
    
            var eXCEPTIONLOGMETHODParameter = eXCEPTIONLOGMETHOD != null ?
                new ObjectParameter("EXCEPTIONLOGMETHOD", eXCEPTIONLOGMETHOD) :
                new ObjectParameter("EXCEPTIONLOGMETHOD", typeof(string));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction("EXCEPTION_LOG_SP", eXCEPTIONLOGDESCRIPTIONParameter, eXCEPTIONLOGDATEParameter, eXCEPTIONLOGSTATUSCODEParameter, eXCEPTIONLOGCONTROLLERParameter, eXCEPTIONLOGMETHODParameter);
        }
    }
}
