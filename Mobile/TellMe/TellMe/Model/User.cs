using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;

namespace TellMe.Model {

    [JsonObject(MemberSerialization.OptOut)]
    public class User {

        public DateTime birthday { get; set; }
        public string level { get; set; }
        public int points { get; set; }
        public DateTime signUpDate { get; set; }
        public int id { get; set; }
        public string login { get; set; }
        public string email { get; set; }

        public override string ToString()
        {
            return $"#{id} {login}: {level}, {points}";
        }
    }
}
