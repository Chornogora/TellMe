using System;
using System.Collections.Generic;
using System.Text;

namespace TellMe.Model
{
    public class UserCache {

        public int id { get; set; }

        public UserCache() { }
        public UserCache(User user) => this.id = user.id;
    }
}
