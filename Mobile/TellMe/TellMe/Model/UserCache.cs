using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;

namespace TellMe.Model
{
    [JsonObject(MemberSerialization.OptOut)]
    public class UserCache
    {
        public int id { get; set; }
        public DateTime lastLogin { get; set; }

        public UserCache() { }
        public UserCache(User user)
        {
            this.id = user.id;
            this.lastLogin = DateTime.Now;
        }
    }
}
