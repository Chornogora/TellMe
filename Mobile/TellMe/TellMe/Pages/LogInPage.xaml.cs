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

namespace TellMe.Pages {

    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class LogInPage : ContentPage {

        private List<Entry> Inputs = new List<Entry>();
        private Dictionary<Entry, Validator> Validators = new Dictionary<Entry, Validator>();
        private Dictionary<Entry, Alert> Alerts = new Dictionary<Entry, Alert>();

        public LogInPage() {

            InitializeComponent();

            Inputs.Add(Login);
            Inputs.Add(Password);

            Validators.Add(Login, () => Login.Text != null && Constants.LoginPattern.IsMatch(Login.Text));
            Validators.Add(Password, () => Password.Text != null && Constants.PasswordPattern.IsMatch(Password.Text));

            Alerts.Add(Login, new Alert("Authorization failed", "Login is invalid", "OK", DisplayAlert));
            Alerts.Add(Password, new Alert("Authorization failed", "Password is invalid", "OK", DisplayAlert));

            ReturnButton.Clicked += ReturnButton_Clicked;
            LogInButton.Clicked += LogInButton_Clicked;

            Constants.ApplyTextChangedHandler(Inputs, Input_TextChanged);
        }

        private bool Validate(bool Messages) => Constants.Validate(Inputs, Validators, Alerts, Constants.DefaultInvalidHandler, Constants.DefaultValidHandler, Messages);
        private void Input_TextChanged(object sender, TextChangedEventArgs e) => Validate(false);

        private void LogInButton_Clicked(object sender, EventArgs e) {
            if (Validate(true))
                LogInAsync(Login.Text, Password.Text);
        }

        private async void LogInAsync(string Login, string Password) {

            LoadingHole.IsRunning = true;

            try {
                await Task.Run(() => {
                    string userJSON = App.ObjectManager.Resolve<DataProvider>().LogIn(Login, Password);
                    User user = JsonConvert.DeserializeObject<User>(userJSON.Substring(0, userJSON.Length - 1));
                    App.RegistrateUserConfig(user);
                    App.Current.MainPage = new ProfilePage();
                });
            } catch(NoConnectionException) {
                await DisplayAlert("Error", "No Internet connection", "OK");
            } catch (InvalidLoginException) {
                await DisplayAlert("Error", "User with such login doesn't exists", "OK");
            } catch (InvalidPasswordException) {
                await DisplayAlert("Error", "Wrong password", "OK");
            } finally {
                LoadingHole.IsRunning = false;
            }
        }

        protected override bool OnBackButtonPressed() { GoBack(); return true; }
        private void ReturnButton_Clicked(object sender, EventArgs e) => GoBack();
        private void GoBack() => App.Current.MainPage = new HelloPage();
    }
}