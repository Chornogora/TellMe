using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using TellMe.Server;
using TellMe.Model;

using Newtonsoft.Json;

using Autofac;

namespace TellMe {

    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class LogInPage : ContentPage {

        public LogInPage() {
            
            InitializeComponent();

            ReturnButton.Clicked += ReturnButton_Clicked;
            LogInButton.Clicked += LogInButton_Clicked;
        }

        private void LogInButton_Clicked(object sender, EventArgs e) {

            if(Login.Text == null) {

                DisplayAlert("Autorization failed", "Login mustn't be empty", "OK");
                return;
            }

            if (Password.Text == null) {

                DisplayAlert("Autorization failed", "Password mustn't be empty", "OK");
                return;
            }

            LogInAsync(Login.Text, Password.Text);
        }
        

        private async void LogInAsync(string Login, string Password) {

            LoadingHole.IsRunning = true;

            try {

                await Task.Run(() => {

                    string userJSON = App.ObjectManager.Resolve<DataProvider>().LogIn(Login, Password);
                    User user = JsonConvert.DeserializeObject<User>(userJSON.Substring(0, userJSON.Length - 1));
                    App.RegistrateUserConfig(user);
                    App.Current.MainPage = App.ObjectManager.Resolve<ProfilePage>(new TypedParameter(typeof(User), user)); 
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