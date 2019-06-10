using System;
using System.Collections.Generic;
using System.Text;

using Newtonsoft.Json;

namespace TellMe.Model
{
    [JsonObject(MemberSerialization.OptOut)]
    public class LessonTask
    {
        public string type { get; set; }
        public string testPicture { get; set; }
        public string testText { get; set; }

        public List<Variant> variants { get; set; }

        public int id { get; set;}
        public int number { get; set;}
        public string content { get; set; }

        public List<Word> words { get; set; }

        public bool IsTest() { return variants != null && variants.Count != 0; }
        public bool IsGrammar() { return content != null && content != ""; }
        public bool IsLexical() { return words != null && words.Count != 0; }
    }
}
