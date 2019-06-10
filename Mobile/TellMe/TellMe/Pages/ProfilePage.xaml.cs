using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using TellMe.Model;
using TellMe.Server;

using Newtonsoft.Json;
using Autofac;

namespace TellMe.Pages {

    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class ProfilePage : ContentPage {

        public User CurrentUser { get; private set; }

        public ProfilePage() {

            InitializeComponent();

            this.Appearing += ProfilePage_Appearing;
            Lessons.Clicked += (sender, e) => { App.Current.MainPage = new LessonListPage(); };
        }

        private void ProfilePage_Appearing(object sender, EventArgs e)
        {
            try { CurrentUser = JsonConvert.DeserializeObject<User>(App.ObjectManager.Resolve<DataProvider>().GetUserInfo(App.CurrentUser.id)); }
            catch (NoConnectionException) { DisplayAlert("Error", "No Internet connection", "OK"); }

            App.U = CurrentUser;

            ULogin.Text = CurrentUser.login;
            ULevel.Text = CurrentUser.level;

            int LevelId = Common.Levels.IndexOf(CurrentUser.level);
            int MaxPoints = Common.Points[LevelId + (LevelId == Common.Levels.Count - 1 ? 0 : 1)];

            LevelProgress.Progress = (double)CurrentUser.points / MaxPoints;
            UProgress.Text = CurrentUser.points + "/" + MaxPoints;

            try {
                if (CurrentUser.avatar == null)
                    Avatar.Source = ImageSource.FromUri(new Uri("http://tellmeprod.herokuapp.com/images/anonymous.jpg"));
                else
                    try { Avatar.Source = ImageSource.FromUri(new Uri(Constants.AVATAR_STORAGE_PATH + CurrentUser.avatar)); }
                    catch { Avatar.Source = ImageSource.FromUri(new Uri("http://tellmeprod.herokuapp.com/images/anonymous.jpg")); }
            } catch (NoConnectionException) { DisplayAlert("Error", "No Internet connection", "OK"); }
        }

        protected override bool OnBackButtonPressed() {
            App.Current.MainPage = App.ObjectManager.Resolve<HelloPage>();
            return true;
        }
    }
}