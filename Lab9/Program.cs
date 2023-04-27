using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using System.Xml.Serialization;
using PTLab3_linq.Resources;

namespace PTLab3_linq
{
    class Program
    {
        static void Main(string[] args)
        {
            List<Car> myCars = new List<Car>(){
                new Car("E250", new Engine(1.8, 204, "CGI"), 2009),
                new Car("E350", new Engine(3.5, 292, "CGI"), 2009),
                new Car("A6", new Engine(2.5, 187, "FSI"), 2012),
                new Car("A6", new Engine(2.8, 220, "FSI"), 2012),
                new Car("A6", new Engine(3.0, 295, "TFSI"), 2012),
                new Car("A6", new Engine(2.0, 175, "TDI"), 2011),
                new Car("A6", new Engine(3.0, 309, "TDI"), 2011),
                new Car("S6", new Engine(4.0, 414, "TFSI"), 2012),
                new Car("S8", new Engine(4.0, 513, "TFSI"), 2012)
            };

            // Exercise 1
            var exc1_1 = from car in myCars
                       where car.model == "A6"
                       select new
                       {
                           engineType = car.motor.model == "TDI" ? "diesel" : "petrol",
                           hppl = car.motor.horsePower / car.motor.displacement
                       };

            var exc1_2 = from elem in exc1_1
                         group elem.hppl by elem.engineType;

            foreach (var group in exc1_2)
            {
                double avgValue = group.Sum() / group.Count();
                Console.WriteLine("{0}: {1}", group.Key, avgValue);
            }


            // EXERCISE 2
            XmlSerializer ser = new XmlSerializer(myCars.GetType(), new XmlRootAttribute("cars"));
            // A FileStream is used to write the file.
            FileStream sfs = new FileStream("CarsCollection.xml", FileMode.Create);
            ser.Serialize(sfs, myCars);
            sfs.Close();

            List<Car> desCars = new List<Car>();
            XmlSerializer deser = new XmlSerializer(myCars.GetType(), new XmlRootAttribute("cars"));
            // A FileStream is used to write the file.
            XmlReader dfs = XmlReader.Create("CarsCollection.xml");
            desCars = (List<Car>)ser.Deserialize(dfs);
            dfs.Close();

            Console.ReadKey();
        }
    }
}
