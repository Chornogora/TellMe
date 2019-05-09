using System;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using Android.Content.Res;

namespace TellMe
{
    public partial class App : Application {

        public App()
        {
            InitializeComponent();
            this.MainPage = DependencyService.Get<HelloPage>();
        }

        protected override void OnStart()
        {
            
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}
