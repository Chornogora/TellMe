using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using TellMe.Model;
using TellMe.Server;

using Autofac;
using Newtonsoft.Json;

namespace TellMe.Pages
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class LessonListPage : ContentPage
    {
        private List<Lesson> lessons = new List<Lesson>();
        private List<Progress> progresses = new List<Progress>();

        public LessonListPage()
        {
            InitializeComponent();

            try {
                lessons = JsonConvert.DeserializeObject<List<Lesson>>(App.ObjectManager.Resolve<DataProvider>().GetLessons());
                progresses = JsonConvert.DeserializeObject<List<Progress>>(App.ObjectManager.Resolve<DataProvider>().GetProgress(App.CurrentUser.id));
            } catch (NoConnectionException) { DisplayAlert("Error", "No Internet connection", "OK"); }

            List<IGrouping<string, Lesson>> LessonsByLevel = lessons.GroupBy(L => L.level).OrderBy(L => Common.Points[Common.Levels.IndexOf(L.Key)]).ToList();

            foreach (IGrouping<string, Lesson> LessonsLevelGroup in LessonsByLevel) {

                StackLayout SL = new StackLayout();
                SL.Style = (Style)App.Current.Resources["List"];

                Button LevelHeader = new Button();

                LevelHeader.Text = LessonsLevelGroup.Key;
                LevelHeader.Style = (Style)App.Current.Resources["HeaderSign"];

                SL.Children.Add(LevelHeader);

                foreach (Lesson L in LessonsLevelGroup) {

                    if (!L.isOpened)
                        continue;

                    Progress CurrentLessonProgress = progresses.SingleOrDefault(P => P.lesson_id == L.id);
                    bool isUnlocked = CurrentLessonProgress != null && Common.Levels.IndexOf(L.level) <= Common.Levels.IndexOf(App.U.level);

                    StackLayout SLL = new StackLayout();
                    SLL.Style = (Style)Resources["LessonLevelBlock"];
                    
                    Button LessonView = new Button();
                    LessonView.Style = (Style)Resources[isUnlocked ? (CurrentLessonProgress.isDone ? "PassedLesson" : "NotPassedLesson") : "LockedLesson"];
                    LessonView.Text = L.name;
                    LessonView.Clicked += (sender, e) => { if (isUnlocked) App.Current.MainPage = new LessonPage(L, CurrentLessonProgress); };

                    ProgressBar LessonProgressBar = new ProgressBar();
                    LessonProgressBar.Style = (Style)Resources["LessonProgress"];

                    try { LessonProgressBar.Progress = CurrentLessonProgress == null? 0 : (double)CurrentLessonProgress.taskPassedNumber / App.ObjectManager.Resolve<DataProvider>().GetTasksCount(L.id); }
                    catch(NoSuchLessonException) { }
                    catch (NoConnectionException) { DisplayAlert("Error", "No Internet connection", "OK"); }

                    SLL.Children.Add(LessonView);
                    SLL.Children.Add(LessonProgressBar);

                    SL.Children.Add(SLL);
                }

                LessonsList.Children.Add(SL);
            }
        }

        protected override bool OnBackButtonPressed()
        {
            App.Current.MainPage = new ProfilePage();
            return true;
        }
    }
}