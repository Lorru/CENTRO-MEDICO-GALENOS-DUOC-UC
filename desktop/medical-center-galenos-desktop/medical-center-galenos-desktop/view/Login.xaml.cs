using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using medical_center_galenos_desktop.service;
using medical_center_galenos_desktop.model;
using MahApps.Metro.Controls.Dialogs;

namespace medical_center_galenos_desktop.view
{
    /// <summary>
    /// Lógica de interacción para Login.xaml
    /// </summary>
    public partial class Login
    {
        public Login()
        {
            InitializeComponent();
        }


        private void btnEnter_Click(object sender, RoutedEventArgs e)
        {
            if (textBoxEmail.Text == "")
            {

                this.ShowMessageAsync("Centro Médico Galenos.", "El CORREO es requerido.");

            }
            else if (passwordBoxPassword.Password == "")
            {

                this.ShowMessageAsync("Centro Médico Galenos.", "La CLAVE es requerida.");

            }
            else
            {

                User user = new User();
                USER userExist = user.login(textBoxEmail.Text, passwordBoxPassword.Password);

                if (userExist == null)
                {

                    this.ShowMessageAsync("Centro Médico Galenos.", "El CORREO o CLAVE son incorrectos.");

                }
                else
                {

                    Menu menu = new Menu();
                    menu.Show();
                    this.Hide();

                }

            }
        }

        private void btnExit_Click(object sender, RoutedEventArgs e)
        {
            Environment.Exit(0);
        }
    }
}
