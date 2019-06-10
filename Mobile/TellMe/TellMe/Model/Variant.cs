using System;
using System.Collections.Generic;
using System.Text;

using Newtonsoft.Json;

namespace TellMe.Model
{
    [JsonObject(MemberSerialization.OptOut)]
    public class Variant
    {
        public int id { get; set; }
        public int number { get; set; }
        public string rightEquivalent { get; set; }
        public string text { get; set; }
    }
}
