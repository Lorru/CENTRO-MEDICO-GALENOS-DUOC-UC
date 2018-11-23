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

namespace medical_center_galenos_desktop.view
{
    /// <summary>
    /// Lógica de interacción para Menu.xaml
    /// </summary>
    public partial class Menu
    {
        public Menu()
        {
            InitializeComponent();
        }

        private void menuItemExit_Click(object sender, RoutedEventArgs e)
        {
            Environment.Exit(0);
        }


        private void menuItemPatients_Click(object sender, RoutedEventArgs e)
        {
            Patients patients = new Patients();

            patients.Show();
            this.Hide();
        }

        private void menuItemSpecialists_Click(object sender, RoutedEventArgs e)
        {
            Specialists specialists = new Specialists();

            specialists.Show();
            this.Hide();
        }

        private void menuItemPersonals_Click(object sender, RoutedEventArgs e)
        {
            Personals personals = new Personals();

            personals.Show();
            this.Hide();
        }

        private void menuItemBranchOffices_Click(object sender, RoutedEventArgs e)
        {
            BranchOffices branchOffices = new BranchOffices();

            branchOffices.Show();
            this.Hide();
        }

        private void menuItemSchedules_Click(object sender, RoutedEventArgs e)
        {
            Schedules schedules = new Schedules();

            schedules.Show();
            this.Hide();

        }

        private void menuItemForecasts_Click(object sender, RoutedEventArgs e)
        {
            Forecasts forecasts = new Forecasts();

            forecasts.Show();
            this.Hide();
        }

        private void menuItemPaymentsType_Click(object sender, RoutedEventArgs e)
        {
            PaymentsType paymentsType = new PaymentsType();

            paymentsType.Show();
            this.Hide();

        }

        private void menuItemBills_Click(object sender, RoutedEventArgs e)
        {
            Bills bills = new Bills();

            bills.Show();
            this.Hide();

        }

        private void menuItemStatesMedicalTime_Click(object sender, RoutedEventArgs e)
        {
            StatesMedicalTime statesMedicalTime = new StatesMedicalTime();

            statesMedicalTime.Show();
            this.Hide();
        }

        private void menuItemCategorys_Click(object sender, RoutedEventArgs e)
        {
            Categorys categorys = new Categorys();

            categorys.Show();
            this.Hide();
        }

        private void menuItemSpecialtys_Click(object sender, RoutedEventArgs e)
        {
            Specialtys specialtys = new Specialtys();

            specialtys.Show();
            this.Hide();
        }

        private void menuItemProfiles_Click(object sender, RoutedEventArgs e)
        {
            Profiles profiles = new Profiles();

            profiles.Show();
            this.Hide();
        }

        private void menuItemRoles_Click(object sender, RoutedEventArgs e)
        {

            Roles roles = new Roles();

            roles.Show();
            this.Hide();
        }
    }
}
