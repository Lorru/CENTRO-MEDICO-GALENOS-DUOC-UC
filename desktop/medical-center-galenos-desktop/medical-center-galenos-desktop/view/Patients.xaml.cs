﻿using System;
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

namespace medical_center_galenos_desktop.view
{
    /// <summary>
    /// Lógica de interacción para Patients.xaml
    /// </summary>
    public partial class Patients
    {
        public Patients()
        {
            InitializeComponent();
            this.PatientGetFindAllInactive();
        }

        private void PatientGetFindAllInactive()
        {

            Patient patient = new Patient();

            dataGridPatient.ItemsSource = patient.findAllInactive();
            dataGridPatient.IsReadOnly = true;

        }

        private void btnReturn_Click(object sender, RoutedEventArgs e)
        {
            Menu menu = new Menu();
            menu.Show();
            this.Hide();
        }

        private void btnUpdate_Click(object sender, RoutedEventArgs e)
        {
            this.PatientGetFindAllInactive();
        }
    }
}
