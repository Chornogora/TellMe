using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;

using Autofac;
using TellMe.Server;
using Newtonsoft.Json;

namespace TellMe.Model
{
    [JsonObject(MemberSerialization.OptOut)]
    public class Lesson : IEnumerable<LessonTask>
    {
        public int id { get; set; }
        public string name { get; set; }
        public int points { get; set; }
        public string level { get; set; }
        public bool isOpened { get; set; }

        [JsonIgnore]
        private List<LessonTask> Tasks = new List<LessonTask>();

        public void LoadTasks()
        {
            Tasks = JsonConvert.DeserializeObject<List<LessonTask>>(App.ObjectManager.Resolve<DataProvider>().GetLessonTasks(id)).OrderBy(T => T.number).ToList();
        }

        public IEnumerator<LessonTask> GetEnumerator() => new Constants.Enumerator<LessonTask>(Tasks);
        IEnumerator IEnumerable.GetEnumerator() => this.GetEnumerator();
    }
}
