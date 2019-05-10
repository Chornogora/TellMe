using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using TellMe.Server;

namespace TellMe {

    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class LogInPage : ContentPage {

        public LogInPage() {
            
            InitializeComponent();

            ReturnButton.Clicked += ReturnButton_Clicked;
            LogInButton.Clicked += LogInButton_Clicked;
        }

        private void LogInButton_Clicked(object sender, EventArgs e) => LogInAsync(Login.Text, Password.Text);
        

        private async void LogInAsync(string Login, string Password) {

            LoadingHole.IsRunning = true;

            try {

                await Task.Run(() => {

                    DependencyService.Get<DataProvider>().LogIn(Login, Password);
                    App.Current.MainPage = DependencyService.Get<LessonsPage>();
                });
            }
            catch (InvalidLoginException) {

                await DisplayAlert("Error", "User with such login doesn't exists", "OK");
            }
            catch (InvalidPasswordException) {

                await DisplayAlert("Error", "Wrong password", "OK");
            }
            finally {

                LoadingHole.IsRunning = false;
            }
        }

        private void ReturnButton_Clicked(object sender, EventArgs e) => GoBack();

        protected override bool OnBackButtonPressed() {

            GoBack();
            return true;
        }

        private void GoBack() => App.Current.MainPage = new HelloPage();
    }
}