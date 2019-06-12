using System;
using System.Collections.Generic;
using System.Text;

using Newtonsoft.Json;
using TellMe.Server;
using Autofac;

namespace TellMe.Model
{
    public class Progress
    {
        public int id { get; set; }
        public int lesson_id { get; set; }
        public int user_id { get; set; }
        public int taskPassedNumber { get; set; }
        public bool isDone { get; set; }

        public bool Pass(bool isTheory, LessonTask LT, string answer = null)
        {
            if (LT.number <= taskPassedNumber + 1) {
                try
                {
                    if (isTheory)
                    {
                        try { App.ObjectManager.Resolve<DataProvider>().PassTheory(id, LT.id); }
                        catch (NoConnectionException) { return false; }
                        catch (NoSuchProgressException) { return false; }
                    }
                    else
                    {
                        try { App.ObjectManager.Resolve<DataProvider>().PassTest(id, LT.id, answer); }
                        catch (NoConnectionException) { return false; }
                        catch (NoSuchProgressException) { return false; }
                        catch (WrongAnswerException) { return false; }
                    }

                    if (LT.number == taskPassedNumber + 1)
                        taskPassedNumber++;

                    if (taskPassedNumber == App.ObjectManager.Resolve<DataProvider>().GetTasksCount(lesson_id)) {
                        App.U.points += JsonConvert.DeserializeObject<Lesson>(App.ObjectManager.Resolve<DataProvider>().GetLesson(lesson_id)).points;
                        isDone = true;

                        int LevelIndex = Common.Levels.IndexOf(App.U.level);
                        if (App.U.points >= Common.Points[LevelIndex])
                            App.U.level = Common.Levels[LevelIndex + 1];
                    }
                }
                catch (NoConnectionException) { return false; }
            }
            return true;
        }
    }
}
