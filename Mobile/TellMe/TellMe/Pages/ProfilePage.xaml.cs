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

            CurrentUser = JsonConvert.DeserializeObject<User>(App.ObjectManager.Resolve<DataProvider>().GetUserInfo(App.CurrentUser.id));
            InitializeComponent();

            this.Appearing += (sender, e) =>
            {
                DisplayAlert("User", CurrentUser.ToString(), "OK");

                int LevelId = Common.Levels.IndexOf(CurrentUser.level);
                int MaxPoints = Common.Points[LevelId + (LevelId == Common.Levels.Count - 1 ? 0 : 1)];

                DisplayAlert("Progress", CurrentUser.points + "/" + MaxPoints, "OK");
            };
        }

        protected override bool OnBackButtonPressed() {
            App.Current.MainPage = App.ObjectManager.Resolve<HelloPage>();
            return true;
        }
    }
}