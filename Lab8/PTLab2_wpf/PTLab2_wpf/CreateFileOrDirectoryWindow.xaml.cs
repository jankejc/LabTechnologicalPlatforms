using System;
using System.Collections.Generic;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Forms;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using Application = System.Windows.Forms.Application;

namespace PTLab2_wpf
{
    /// <summary>
    /// Interaction logic for CreateFileOrDirectoryWindow.xaml
    /// </summary>
    public partial class CreateFileOrDirectoryWindow : Window
    {
        private MainWindow mainWindow;
        private TreeViewItem callingItem;

        /// <summary>
        /// Interaction logic for CreateFileOrDirectoryWindow.xaml
        /// Constructor with an item as argument.
        /// </summary>
        public CreateFileOrDirectoryWindow(TreeViewItem item)
        {
            WindowStartupLocation = System.Windows.WindowStartupLocation.CenterScreen;
            InitializeComponent();
            this.mainWindow = System.Windows.Application.Current.MainWindow as MainWindow;
            this.callingItem = item;
        }


        /// <summary>
        /// Handle SubmitButton and send information to MainWindow to create specific file or dir.
        /// </summary>
        private void CreateItem_Click(object sender, RoutedEventArgs e)
        {
            // Decide what item to create.
            bool isCreateFile = false;
            if ((bool)FileRadioButton.IsChecked)
            {
                isCreateFile = (bool)FileRadioButton.IsChecked;
            }
            else if ((bool)DirectoryRadioButton.IsChecked)
            {
                isCreateFile = false;
            }
            else
            {
                this.mainWindow.CloseCreateFileOrDirectoryWindow();
            }

            this.mainWindow.CreateFileOrDirectory(this.callingItem, NewItemName.Text, isCreateFile,
                (bool)ReadOnlyCheck.IsChecked, (bool)ArchiveCheck.IsChecked,
                (bool)HiddenCheck.IsChecked, (bool)SystemCheck.IsChecked);
        }


        /// <summary>
        /// Handle CancelButton and send information to MainWindow close current window.
        /// </summary>
        private void CancelItem_Click(object sender, RoutedEventArgs e)
        {
            this.mainWindow.CloseCreateFileOrDirectoryWindow();
        }
    }
}
