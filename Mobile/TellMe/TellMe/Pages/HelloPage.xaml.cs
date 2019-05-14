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

namespace TellMe {

    // Learn more about making custom code visible in the Xamarin.Forms previewer
    // by visiting https://aka.ms/xamarinforms-previewer
    [DesignTimeVisible(true)]
    public partial class HelloPage : ContentPage {

        public HelloPage() {

            InitializeComponent();

            try {

                Thread T = new Thread(() => App.ObjectManager.Resolve<DataProvider>().Init());
                T.Start();
            }
            catch (InitFailedException) {

                DisplayAlert("Error", "Server is not responding", "OK");
                App.Close();
            }

            SignUpButton.Clicked += SignUpButton_Clicked;
            LogInButton.Clicked += LogInButton_Clicked;
        }

        private void LogInButton_Clicked(object sender, EventArgs e) {

            App.Current.MainPage = App.ObjectManager.Resolve<LogInPage>();
        }

        private void SignUpButton_Clicked(object sender, EventArgs e) {

            App.Current.MainPage = App.ObjectManager.Resolve<SignUpPage>();
        }
    }
}
