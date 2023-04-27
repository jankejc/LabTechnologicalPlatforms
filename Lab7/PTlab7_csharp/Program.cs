using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PTlab7_csharp
{
    class Program
    {
        static void Main(string[] args)
        {
            if (args.Length == 0)
            {
                Console.WriteLine("Please enter a folder path as console argument.");
                return;
            }

            /// CORE PROGRAM
            // Build tree.
            FolderVisualizer.VisualizeFolderTree(args[0]);

            // Print date of the oldest file.
            DirectoryInfo directoryInfo = new DirectoryInfo(args[0]);
            DateTime oldest = FolderVisualizer.GetDateOfOldestFile(directoryInfo, args[0], DateTime.Now);
            Console.WriteLine($"\nOldest file: {oldest}\n");

            SortedDictionary<string, long> dictionary = FolderVisualizer.GetImmediateFiles(args[0]);
            string serializeFile = "DataFile.dat";
            FolderVisualizer.Serialize(dictionary, serializeFile);
            FolderVisualizer.Deserialize(serializeFile);

            /// END OF PROGRAM 
            Console.WriteLine("\nPress any key to exit the process...");
            Console.ReadKey();
            return;
        }
    }
}
