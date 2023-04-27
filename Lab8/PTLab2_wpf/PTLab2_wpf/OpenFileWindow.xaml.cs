using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace PTLab2_wpf
{
    /// <summary>
    /// Interaction logic for OpenFileWindow.xaml
    /// Constructor with an item as argument.
    /// </summary>
    public partial class OpenFileWindow : Window
    {
        public OpenFileWindow(TreeViewItem item)
        {
            WindowStartupLocation = System.Windows.WindowStartupLocation.CenterScreen;
            InitializeComponent();
            FileTextBlock.Text = File.ReadAllText(item.Tag.ToString());
        }
    }
}
