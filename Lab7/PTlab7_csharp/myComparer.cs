using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PTlab7_csharp
{
    [Serializable]
    class myComparer : IComparer<string>
    {
        public int Compare(string x, string y)
        {
            if (x.Length > y.Length)
                return 1;
            else if (x.Length < y.Length)
                return -1;

            return x.CompareTo(y);
        }
    }
}
