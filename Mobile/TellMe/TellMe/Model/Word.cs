using System;
using System.Collections.Generic;
using System.Text;

using Newtonsoft.Json;

namespace TellMe.Model
{
    [JsonObject(MemberSerialization.OptOut)]
    public class Word
    {
        public int id { get; set; }
        public string description { get; set; }
        public string name { get; set; }
        public string translation { get; set; }
    }
}
