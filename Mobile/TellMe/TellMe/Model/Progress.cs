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

        public bool Pass(bool isTheory, int taskNum, string answer = null)
        {
            if (taskNum <= taskPassedNumber + 1)
            {
                try
                {
                    if(taskNum == taskPassedNumber + 1)
                    {
                        if (isTheory)
                        {
                            try { App.ObjectManager.Resolve<DataProvider>().PassTheory(id); }
                            catch (NoConnectionException) { return false; }
                            catch (NoSuchProgressException) { return false; }
                        }
                        else
                        {
                            try { App.ObjectManager.Resolve<DataProvider>().PassTest(id, answer); }
                            catch (NoConnectionException) { return false; }
                            catch (NoSuchProgressException) { return false; }
                            catch (WrongAnswerException) { return false; }
                        }
                        taskPassedNumber++;
                    }
                    if (taskPassedNumber == App.ObjectManager.Resolve<DataProvider>().GetTasksCount(lesson_id))
                    {
                        App.U.points += JsonConvert.DeserializeObject<Lesson>(App.ObjectManager.Resolve<DataProvider>().GetLesson(lesson_id)).points;
                        isDone = true;
                    }
                }
                catch (NoConnectionException) { return false; }
            }
            return true;
        }
    }
}
