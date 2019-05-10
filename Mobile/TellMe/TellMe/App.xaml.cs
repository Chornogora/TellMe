using System;
using System.Threading;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using Android.Content.Res;

using TellMe.Server;

namespace TellMe
{
    public partial class App : Application {

        public static void Close()
        {
            Android.OS.Process.KillProcess(Android.OS.Process.MyPid());
        }

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
