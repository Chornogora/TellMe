using Autofac;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TellMe.Server;
using Newtonsoft.Json;
using System.Linq;

namespace TellMe.Model {

    public static class Common {

        public static List<string> Levels = new List<string>();
        public static List<int> Points = new List<int>();
        
        public static void Load() {
            string LevelsJson = App.ObjectManager.Resolve<DataProvider>().GetLevels();
            Dictionary<string, int> LPoints = JsonConvert.DeserializeObject<Dictionary<string, int>>(LevelsJson).
                OrderBy(kvp => kvp.Value).ToDictionary(kvp => kvp.Key, kvp => kvp.Value);

            Levels = LPoints.Keys.ToList();
            Points = LPoints.Values.ToList();
        }
    }
}
