using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using TellMe.Server;

using Autofac;

namespace TellMe {

    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class SignUpPage : ContentPage {

        public SignUpPage() {

            InitializeComponent();

            ReturnButton.Clicked += ReturnButton_Clicked;
            SignUpButton.Clicked += SignUpButton_Clicked;
        }

        private void SignUpButton_Clicked(object sender, EventArgs e) {

            if (Login.Text == null) {

                DisplayAlert("Registration failed", "Login mustn't be empty", "OK");
                return;
            }

            if (Password.Text == null) {

                DisplayAlert("Registration failed", "Password mustn't be empty", "OK");
                return;
            }

            if (Email.Text == null) {

                DisplayAlert("Registration failed", "Email mustn't be empty", "OK");
                return;
            }

            if (Password.Text != PasswordConfirm.Text) {

                DisplayAlert("Registration failed", "Your passwords don't match", "OK");
                return;
            }

            SignUpAsync(Login.Text, Password.Text, Email.Text, Birth.Date);
        }

        private async void SignUpAsync(string Login, string Password, string Email, DateTime Birth) {

            LoadingHole.IsRunning = true;

            try {

                await Task.Run(() => {

                    App.ObjectManager.Resolve<DataProvider>().SignUp(Login, Password, Email, Birth);
                    App.Current.MainPage = App.ObjectManager.Resolve<ActivationPage>();
                });
            }
            catch (UserExistsException) {

                await DisplayAlert("Registration failed", "User with such login already exists", "OK");
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

        private void GoBack() => App.Current.MainPage = App.ObjectManager.Resolve<HelloPage>();

        protected override void OnSizeAllocated(double width, double height) {

            base.OnSizeAllocated(width, height);

            if(width > height) 
                HeaderGrid.IsVisible = false;
            else
                HeaderGrid.IsVisible = true;
        }
    }
}