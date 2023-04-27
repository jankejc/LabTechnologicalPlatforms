using System;
using System.IO;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization;

namespace PTlab7_csharp
{
    public static class FolderVisualizer
    {
        public static void VisualizeFolderTree(string folderPath, string spaces = "")
        {
            // Get the sub files and dirs.
            string[] folderFiles = Directory.GetFiles(folderPath);
            string[] folderSubdirectories = Directory.GetDirectories(folderPath);

            int filesCounter = folderFiles.Length + folderSubdirectories.Length;
            string[] dirsAtSrcPath = folderPath.Split('\\');

            string folderRahs = GetFileRahs(new FileInfo(folderPath));
            Console.WriteLine(spaces + dirsAtSrcPath[dirsAtSrcPath.Length - 1] 
                + $" ({filesCounter})" + $" {folderRahs}");

            // Recurse into subdirectories of this directory. 
            foreach (string subdirectory in folderSubdirectories)
                VisualizeFolderTree(subdirectory, spaces + "    ");

            // Process the list of files found in the directory.
            foreach (string filePath in folderFiles)
            {
                string fileRahs = GetFileRahs(new FileInfo(filePath));
                long fileSize = GetFileSize(filePath);
                Console.WriteLine(spaces + "    " + Path.GetFileName(filePath) + $" {fileSize} bytes"
                     + $" {fileRahs}");
            }
        }

        public static DateTime GetDateOfOldestFile(this DirectoryInfo directoryInfo, string folderPath, DateTime oldest)
        {
            string[] folderSubdirectories = Directory.GetDirectories(folderPath);

            // Recurse into subdirectories of this directory. 
            foreach (string subdirectory in folderSubdirectories)
            {
                DirectoryInfo directoryInfoNew = new DirectoryInfo(subdirectory);
                GetDateOfOldestFile(directoryInfoNew, subdirectory, oldest);
            }

            // Process the list of files found in the directory.
            foreach (var file in directoryInfo.GetFiles())
                if (file.CreationTime < oldest)
                    oldest = file.CreationTime;

            return oldest;
        }
     
        private static string GetFileRahs(this FileSystemInfo fsi)
        {
            string rahs = "";
            FileAttributes fileAttributes = File.GetAttributes(fsi.FullName);

            rahs += (fileAttributes.HasFlag(FileAttributes.ReadOnly)) ? 'r' : '-';
            rahs += (fileAttributes.HasFlag(FileAttributes.Archive)) ? 'a' : '-';
            rahs += (fileAttributes.HasFlag(FileAttributes.Hidden)) ? 'h' : '-';
            rahs += (fileAttributes.HasFlag(FileAttributes.System)) ? 's' : '-';

            return rahs;
        }

        private static long GetFileSize(string filePath)
        {
            FileInfo fileInfo = new FileInfo(filePath);
            return fileInfo.Length;
        }

        public static SortedDictionary<string, long> GetImmediateFiles(string folderPath)
        {
            SortedDictionary<string, long> immediateFiles = new SortedDictionary<string, long>(new myComparer());
            string[] folderFilesMain = Directory.GetFiles(folderPath);
            string[] folderSubdirectoriesMain = Directory.GetDirectories(folderPath);

            foreach (string subdirectory in folderSubdirectoriesMain)
            {
                string[] folderFiles = Directory.GetFiles(subdirectory);
                string[] folderSubdirectories = Directory.GetDirectories(subdirectory);
                int filesCounter = folderFiles.Length + folderSubdirectories.Length;
                string[] dirsAtSrcPath = subdirectory.Split('\\');
                immediateFiles.Add(dirsAtSrcPath[dirsAtSrcPath.Length - 1], filesCounter);
            }
                
            foreach (string filePath in folderFilesMain)
                immediateFiles.Add(Path.GetFileName(filePath), GetFileSize(filePath));
            

            return immediateFiles;
        }

        public static void PrintSortedImmediateFiles(string folderPath)
        {
            SortedDictionary<string, long> immediateFiles = GetImmediateFiles(folderPath);
            foreach(var file in immediateFiles)
                Console.WriteLine($"{file.Key} -> {file.Value}");
        }

        public static void Serialize(SortedDictionary<string, long> dictionary, string serializeFile)
        {
            FileStream fs = new FileStream(serializeFile, FileMode.Create);

            // Construct a BinaryFormatter and use it to serialize the data to the stream.
            BinaryFormatter formatter = new BinaryFormatter();
            try
            {
                formatter.Serialize(fs, dictionary);
            }
            catch (SerializationException e)
            {
                Console.WriteLine("Failed to serialize. Reason: " + e.Message);
                throw;
            }
            finally
            {
                fs.Close();
            }
        }
        public static void Deserialize(string serializeFile)
        {
            // Declare the hashtable reference.
            SortedDictionary<string, long> dictionary = null;

            // Open the file containing the data that you want to deserialize.
            FileStream fs = new FileStream(serializeFile, FileMode.Open);
            try
            {
                BinaryFormatter formatter = new BinaryFormatter();

                // Deserialize the hashtable from the file and
                // assign the reference to the local variable.
                dictionary = (SortedDictionary<string, long>)formatter.Deserialize(fs);
            }
            catch (SerializationException e)
            {
                Console.WriteLine("Failed to deserialize. Reason: " + e.Message);
                throw;
            }
            finally
            {
                fs.Close();
            }

            foreach (var obj in dictionary)
            {
                Console.WriteLine("{0} -> {1}", obj.Key, obj.Value);
            }
        }
    }
}
