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
    /// Lógica de interacción para Personals.xaml
    /// </summary>
    public partial class Personals
    {
        public Personals()
        {
            InitializeComponent();
            this.PersonalGetFindAllInactive();
        }

        private void PersonalGetFindAllInactive()
        {

            Personal personal = new Personal();

            dataGridPersonal.ItemsSource = personal.findAllInactive();
            dataGridPersonal.IsReadOnly = true;

        }

        private void btnReturn_Click(object sender, RoutedEventArgs e)
        {
            Menu menu = new Menu();
            menu.Show();
            this.Hide();
        }

        private void btnUpdate_Click(object sender, RoutedEventArgs e)
        {
            this.PersonalGetFindAllInactive();
        }
    }
}
