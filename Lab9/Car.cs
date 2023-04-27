﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace PTLab3_linq.Resources
{
    [XmlType("car")]
    public class Car
    {
        public string model;
        [XmlElement(ElementName = "engine")]
        public Engine motor;
        public int year;

        public Car() { }
        public Car(string model, Engine motor, int year)
        {
            this.model = model;
            this.motor = motor;
            this.year = year;
        }
    }
}
