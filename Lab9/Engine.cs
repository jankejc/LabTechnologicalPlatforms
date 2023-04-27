using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace PTLab3_linq.Resources
{
    public class Engine
    {
        public double displacement;
        public double horsePower;
        [XmlAttribute]
        public string model;

        public Engine() { }

        public Engine(double displacement, double horsePower, string model)
        {
            this.displacement = displacement;
            this.horsePower = horsePower;
            this.model = model;
        }
    }
}
