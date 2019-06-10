using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;

using Xamarin.Forms;

using Android.App;

using Autofac;

using TellMe.Server;

namespace TellMe.Pages {

    [DesignTimeVisible(true)]
    public partial class HelloPage : ContentPage {

        public HelloPage() {

            InitializeComponent();

            this.Appearing += HelloPage_Appearing;
            SignUpButton.Clicked += (sender, e) => App.Current.MainPage = App.ObjectManager.Resolve<SignUpPage>();
            LogInButton.Clicked += LogInButton_Clicked;
        }

        private void HelloPage_Appearing(object sender, EventArgs e)
        {
            try {
                Task.Run(() => App.ObjectManager.Resolve<DataProvider>().Init());
            } catch (NoConnectionException) {
                DisplayAlert("Error", "No Internet connection", "OK"); return;
            } catch(InitFailedException) {
                DisplayAlert("Error", "No Internet connection", "OK"); return;
            }
        }

        private void LogInButton_Clicked(object sender, EventArgs e)
        {
            if (App.CurrentUser != null && (DateTime.Now - App.CurrentUser.lastLogin).TotalMinutes < Constants.SESSION_LIFETIME_MINUTES)
                App.Current.MainPage = new ProfilePage();
            else
                App.Current.MainPage = App.ObjectManager.Resolve<LogInPage>();
        }
    }
}
