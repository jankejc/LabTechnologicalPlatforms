using System;
using System.Diagnostics;
using System.IO;
using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using WinForms = System.Windows.Forms;

namespace PTLab2_wpf
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private CreateFileOrDirectoryWindow createFileOrDirectoryWindow;


        /// <summary>
        /// Interaction logic for MainWindow.xaml
        /// Constructor.
        /// </summary>
        public MainWindow()
        {
            WindowStartupLocation = System.Windows.WindowStartupLocation.CenterScreen;
            InitializeComponent();
        }


        /// <summary>
        /// Creates file or a directory from informations given externally from CreateFileOrDirectoryWindow.
        /// </summary>
        public void CreateFileOrDirectory(TreeViewItem callingItem, string name, 
            bool isFile, bool isReadOnly, bool isArchive, bool isHidden, bool isSystem)
        {
            if (Regex.IsMatch(name, "^([a-zA-Z0-9_~-]{1,8}).(txt|php|html)$") 
                || Regex.IsMatch(name, "^[a-zA-Z0-9_~-]{1,8}$"))
            {

                TreeViewItem newItem = new TreeViewItem
                {
                    Header = "",
                    Tag = ""
                };

                // isFile = true => Creates file.
                // isFile = false => Creates directory.
                if (isFile && Path.GetExtension(name) != "")
                {
                    FileStream newFileStream = File.Create(Path.Combine(callingItem.Tag.ToString(), name));
                    newItem.Tag = newFileStream.Name;
                    newItem.Header = name;

                    CreateFileContext(ref newItem);
                }
                else if (!isFile)
                {
                    DirectoryInfo newFileDirectoryInfo = Directory.CreateDirectory(Path.Combine(callingItem.Tag.ToString(), name));
                    newItem.Tag = newFileDirectoryInfo.FullName;
                    newItem.Header = name;
                    CreateDirectoryContext(ref newItem);
                }
                else
                {
                    MessageBox.Show("Name must contain: 1-8 digits, a-z, A-Z, 0-9, _, ~, -\n" +
                    "File extensions: .php, .txt, .html", "WARNING");
                    CloseCreateFileOrDirectoryWindow();
                    return;
                }

                callingItem.Items.Add(newItem);

                // Create attributes for the item.
                FileAttributes newItemAttributes = File.GetAttributes(newItem.Tag.ToString());

                if (isReadOnly)
                    newItemAttributes |= FileAttributes.ReadOnly;
                if (isArchive)
                    newItemAttributes |= FileAttributes.Archive;
                if (isHidden)
                    newItemAttributes |= FileAttributes.Hidden;
                if (isSystem)
                    newItemAttributes |= FileAttributes.System;

                File.SetAttributes(newItem.Tag.ToString(), newItemAttributes);
            }
            else
            {
                MessageBox.Show("Name must contain: 1-8 digits, a-z, A-Z, 0-9, _, ~, -\n" +
                    "File extensions: .php, .txt, .html", "WARNING");
            }

            CloseCreateFileOrDirectoryWindow();
        }


        /// <summary>
        /// Close CreateFileOrDirectoryWindow called externally.
        /// </summary>
        public void CloseCreateFileOrDirectoryWindow()
        {
            this.createFileOrDirectoryWindow.Close();
        }


        /// <summary>
        /// Handle File->Open option to open directory by dialog.
        /// </summary>
        private void OpenOption_click(object sender, RoutedEventArgs e)
        {
            var dialog = new WinForms.FolderBrowserDialog();
            WinForms.DialogResult result = dialog.ShowDialog();
            if (dialog.SelectedPath != "")
                ManageTreeView(dialog);
            else
                MessageBox.Show("There is no path!", "WARNING");
        }


        /// <summary>
        /// Handle File->Exit option to quit.
        /// </summary>
        private void ExitOption_click(object sender, RoutedEventArgs e)
        {
            System.Windows.Application.Current.Shutdown();
        }


        /// <summary>
        /// Initialize TreeView from user pick at dialog 
        /// and "pin" it to "MyTreeView" (TreeView tag at XAML).
        /// </summary>
        private void ManageTreeView(WinForms.FolderBrowserDialog dialog)
        {
            TreeViewItem root = new TreeViewItem
            {
                Header = new DirectoryInfo(dialog.SelectedPath).Name,
                Tag = dialog.SelectedPath
            };

            BuildTreeView(ref root);

            CreateDirectoryContext(ref root);

            MyTreeView.Items.Add(root);
        }


        /// <summary>
        /// Build TreeView with root TreeViewItem as input. 
        /// </summary>
        private void BuildTreeView(ref TreeViewItem root)
        {         
            ServiceSubDirectiories(ref root);
            ServiceSubFiles(ref root);
        }


        /// <summary>
        /// Process the list of subdirectiories found in the directory.
        /// </summary>
        private void ServiceSubDirectiories(ref TreeViewItem root)
        {
            string[] folderSubDirectories = Directory.GetDirectories(root.Tag.ToString());
            foreach (string subdirectory in folderSubDirectories)
            {
                TreeViewItem subDirChild = new TreeViewItem
                {
                    Header = new DirectoryInfo(subdirectory).Name,
                    Tag = subdirectory
                };
                CreateDirectoryContext(ref subDirChild);
                root.Items.Add(subDirChild);
                BuildTreeView(ref subDirChild);
            }
        }


        /// <summary>
        /// Process the list of files found in the directory.
        /// </summary>
        private void ServiceSubFiles(ref TreeViewItem root)
        {
            string[] folderFiles = Directory.GetFiles(root.Tag.ToString());
            foreach (string filePath in folderFiles)
            {
                //string fileRahs = GetFileRahs(new FileInfo(filePath));
                TreeViewItem fileChild = new TreeViewItem
                {
                    Header = new FileInfo(filePath).Name,
                    Tag = filePath
                };

                CreateFileContext(ref fileChild);
                root.Items.Add(fileChild);
            }
        }


        /// <summary>
        /// Create rigth-click menu for the file.
        /// </summary>
        private void CreateFileContext(ref TreeViewItem file)
        {
            file.ContextMenu = new ContextMenu();
            file.ContextMenu.Items.Add(OpenFileMenuItemGenerator());
            file.ContextMenu.Items.Add(DeleteMenuItemGenerator());
        }


        /// <summary>
        /// Method generates and return ready-to-use "Open" MenuItem for a file.
        /// </summary>
        private MenuItem OpenFileMenuItemGenerator()
        {
            MenuItem openContextItem = new MenuItem();
            openContextItem.Header = "Open";
            openContextItem.Click += OpenItem_Click;

            return openContextItem;
        }


        /// <summary>
        /// Method generates and return ready-to-use "Delete" MenuItem for a file.
        /// </summary>
        private MenuItem DeleteMenuItemGenerator()
        {
            MenuItem deleteContextItem = new MenuItem();
            deleteContextItem.Header = "Delete";
            deleteContextItem.Click += DeleteItem_Click;

            return deleteContextItem;
        }


        /// <summary>
        /// Create rigth-click menu for the directory.
        /// </summary>
        private void CreateDirectoryContext(ref TreeViewItem dir)
        {
            dir.ContextMenu = new ContextMenu();
            dir.ContextMenu.Items.Add(CreateMenuItemGenerator());
            dir.ContextMenu.Items.Add(DeleteMenuItemGenerator());
        }


        /// <summary>
        /// Method generates and return ready-to-use "Create" MenuItem for a file.
        /// </summary>
        private MenuItem CreateMenuItemGenerator()
        {
            MenuItem createContextItem = new MenuItem();
            createContextItem.Header = "Create";
            createContextItem.Click += CreateItem_Click;

            return createContextItem;
        }


        /// <summary>
        /// Handle request for deleting file or directory.
        /// </summary>
        private void DeleteItem_Click(object sender, System.EventArgs e)
        {
            TreeViewItem item = MyTreeView.SelectedItem as TreeViewItem;
            if (item == null)
            {
                MessageBox.Show("You must select the item first!", "WARNING");
                return;
            }

            FileAttributes itemAttr = File.GetAttributes(item.Tag.ToString());

            // Check whether item is a file or a directory.
            if ((itemAttr & FileAttributes.Directory) == FileAttributes.Directory)
                DeleteDirectory(item, FindParentTreeViewItem(item));
            else
                DeleteFile(item, FindParentTreeViewItem(item));
        }


        /// <summary>
        /// Return parent of the child.
        /// </summary>
        private TreeViewItem FindParentTreeViewItem(object child)
        {
            try
            {
                var parent = VisualTreeHelper.GetParent(child as DependencyObject);
                while ((parent as TreeViewItem) == null)
                {
                    parent = VisualTreeHelper.GetParent(parent);
                }
                return parent as TreeViewItem;
            }
            catch (Exception e)
            {
                //could not find a parent of type TreeViewItem
                return null;
            }
        }

        /// <summary>
        /// Handle "Open" option from the file context.
        /// File must be selected for propper work.
        /// Creates new OpenFileWindow with file item as constructor argument.
        /// </summary>
        private void OpenItem_Click(object sender, System.EventArgs e)
        {
            TreeViewItem fileItem = MyTreeView.SelectedItem as TreeViewItem;
            if (fileItem == null)
            {
                MessageBox.Show("You must select the item first!", "WARNING");
                return;
            }

            OpenFileWindow openFileWindow = new OpenFileWindow(fileItem);
            openFileWindow.Show();
        }


        /// <summary>
        /// Handle "Create" option from the directory context.
        /// Directory must be selected for propper work.
        /// Creates new directory or file in selected directory with specific permissions.
        /// </summary>
        private void CreateItem_Click(object sender, System.EventArgs e)
        {
            TreeViewItem sourceDir = MyTreeView.SelectedItem as TreeViewItem;
            if (sourceDir == null)
            {
                MessageBox.Show("You must select the item first!", "WARNING");
                return;
            }

            this.createFileOrDirectoryWindow = new CreateFileOrDirectoryWindow(sourceDir);

            this.createFileOrDirectoryWindow.Show();
        }


        /// <summary>
        /// Method removes read-only and delete directory and all of its files / subdirs.
        /// </summary>
        private void DeleteDirectory(TreeViewItem dirToDelete, TreeViewItem parent)
        {
            for (int i = dirToDelete.Items.Count - 1; i >= 0; i--)
            {
                TreeViewItem subItem = dirToDelete.Items[i] as TreeViewItem;
                FileAttributes itemAttr = File.GetAttributes(subItem.Tag.ToString());

                // Check whether item is a file or a directory.
                if ((itemAttr & FileAttributes.Directory) == FileAttributes.Directory)
                    DeleteDirectory(subItem, dirToDelete);
                else
                    DeleteFile(subItem, dirToDelete);
            }

            if(parent == null)
            {
                MessageBox.Show("You cannot delete root directory!", "WARNING");
                return;
            }
            // Remove from parent Items list.
            parent.Items.Remove(dirToDelete);

            // Delete dirToDelete from disk.
            RemoveReadOnly(dirToDelete);
            Directory.Delete(dirToDelete.Tag.ToString());
        }


        /// <summary>
        /// Method removes read-only and delete file.
        /// </summary>
        private void DeleteFile(TreeViewItem file, TreeViewItem parent)
        {
            // Remove from parent Items list.
            parent.Items.Remove(file);

            // Delete from disk.
            RemoveReadOnly(file);
            File.Delete(file.Tag.ToString());
        }


        /// <summary>
        /// Removes read-only permission from the file or directory.
        /// </summary>
        private void RemoveReadOnly(TreeViewItem item)
        {
            FileAttributes itemAttr = File.GetAttributes(item.Tag.ToString());

            if (itemAttr.HasFlag(FileAttributes.ReadOnly))
            {
                // Remove read-only attribute and set it to item.
                itemAttr &= ~FileAttributes.ReadOnly;
                File.SetAttributes(item.Tag.ToString(), itemAttr);
            }
        }


        /// <summary>
        /// Displaying RAHS of selected TreeViewItem.
        /// </summary>
        private void DisplayTreeViewItemSelectedRahs(object sender, MouseButtonEventArgs e)
        {
            TreeViewItem item = MyTreeView.SelectedItem as TreeViewItem;
            if (item != null)
                dosAttributesTextBlock.Text = GetRahsOfItemPath(item.Tag.ToString());         
        }


        /// <summary>
        /// Return RAHS string of item from path.
        /// </summary>
        private string GetRahsOfItemPath(string fullPath)
        {
            string rahs = "";
            FileAttributes fileAttributes = File.GetAttributes(fullPath);

            rahs += (fileAttributes.HasFlag(FileAttributes.ReadOnly)) ? 'r' : '-';
            rahs += (fileAttributes.HasFlag(FileAttributes.Archive)) ? 'a' : '-';
            rahs += (fileAttributes.HasFlag(FileAttributes.Hidden)) ? 'h' : '-';
            rahs += (fileAttributes.HasFlag(FileAttributes.System)) ? 's' : '-';

            return rahs;
        }
    }
}
