using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using System.Windows;

namespace medical_center_galenos_desktop
{
    /// <summary>
    /// Lógica de interacción para App.xaml
    /// </summary>
    public partial class App : Application
    {
        protected override void OnStartup(StartupEventArgs e)
        {
            Process process = Process.GetCurrentProcess();

            if (Process.GetProcessesByName(process.ProcessName).Length > 1)
            {
                MessageBox.Show("La Aplicación ya se esta ejecutando", "Centro Médico Galenos.", MessageBoxButton.OK, MessageBoxImage.Warning);
                Environment.Exit(0);
                return;
            }

            base.OnStartup(e);
        }
    }
}
