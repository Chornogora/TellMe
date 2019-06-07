using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using TellMe.Server;

using Autofac;
using System.Text.RegularExpressions;

namespace TellMe.Pages {

    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class SignUpPage : ContentPage {

        private List<Entry> Inputs = new List<Entry>();
        private Dictionary<Entry, Validator> Validators = new Dictionary<Entry, Validator>();
        private Dictionary<Entry, Alert> Alerts = new Dictionary<Entry, Alert>();

        public SignUpPage() {

            InitializeComponent();

            Inputs.Add(Login);
            Inputs.Add(Password);
            Inputs.Add(PasswordConfirm);
            Inputs.Add(Email);

            Validators.Add(Login, () => Login.Text != null && Constants.LoginPattern.IsMatch(Login.Text));
            Validators.Add(Password, () => Password.Text != null && Constants.PasswordPattern.IsMatch(Password.Text));
            Validators.Add(PasswordConfirm, () => PasswordConfirm.Text != null && Password.Text != null && PasswordConfirm.Text.Equals(Password.Text));
            Validators.Add(Email, () => Email.Text != null && Constants.EmailPattern.IsMatch(Email.Text));

            Alerts.Add(Login, new Alert("Registration failed", "Login is invalid", "OK", DisplayAlert));
            Alerts.Add(Password, new Alert("Registration failed", "Password is invalid", "OK", DisplayAlert));
            Alerts.Add(PasswordConfirm, new Alert("Registration failed", "Email is invalid", "OK", DisplayAlert));
            Alerts.Add(Email, new Alert("Registration failed", "Password confirmation failed", "OK", DisplayAlert));

            ReturnButton.Clicked += ReturnButton_Clicked;
            SignUpButton.Clicked += SignUpButton_Clicked;

            Constants.ApplyTextChangedHandler(Inputs, Input_TextChanged);
        }

        private bool Validate(bool Messages) => Constants.Validate(Inputs, Validators, Alerts, Constants.DefaultInvalidHandler, Constants.DefaultValidHandler, Messages);

        private void Input_TextChanged(object sender, TextChangedEventArgs e) => Validate(false);
        private void SignUpButton_Clicked(object sender, EventArgs e) {
            if (Validate(true))
                SignUpAsync(Login.Text, Password.Text, Email.Text, Birth.Date);
        }

        private async void SignUpAsync(string Login, string Password, string Email, DateTime Birth) {

            LoadingHole.IsRunning = true;

            try {
                await Task.Run(() => {
                    App.ObjectManager.Resolve<DataProvider>().SignUp(Login, Password, Email, Birth);
                    App.Current.MainPage = App.ObjectManager.Resolve<ActivationPage>();
                });
            } catch (NoConnectionException) {
                await DisplayAlert("Error", "No Internet connection", "OK");
            } catch (UserExistsException) {
                await DisplayAlert("Registration failed", "User with such login already exists", "OK");
            } finally {
                LoadingHole.IsRunning = false;
            }
        }

        protected override bool OnBackButtonPressed() { GoBack(); return true; }
        private void ReturnButton_Clicked(object sender, EventArgs e) => GoBack();
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