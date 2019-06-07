using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using TellMe.Server;
using TellMe.Model;

using Newtonsoft.Json;
using Autofac;
using Autofac.Core;

namespace TellMe.Pages {

    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class ActivationPage : ContentPage
    {
        private List<Entry> Inputs = new List<Entry>();
        private Dictionary<Entry, Validator> Validators = new Dictionary<Entry, Validator>();
        private Dictionary<Entry, Alert> Alerts = new Dictionary<Entry, Alert>();

        public ActivationPage()
        {            
            InitializeComponent();

            Inputs.Add(Code);
            Validators.Add(Code, () => Code.Text != null || Code.Text.Length == 5);
            Alerts.Add(Code, new Alert("Activation failed", "Activation code is invalid", "OK", DisplayAlert));
            Constants.ApplyTextChangedHandler(Inputs, Input_TextChanged);

            ActivationButton.Clicked += ActivationButton_Clicked;
        }

        private bool Validate(bool Messages) => Constants.Validate(Inputs, Validators, Alerts, Constants.DefaultInvalidHandler, Constants.DefaultValidHandler, Messages);
        protected override void OnAppearing() => DisplayAlert("Registration successful!", "Please check your email for an activation code", "OK");
        private void Input_TextChanged(object sender, TextChangedEventArgs e) => Validate(false);

        private void ActivationButton_Clicked(object sender, EventArgs e) {
            if (Validate(true))
                ActivateAsync(Code.Text);
        }

        private async void ActivateAsync(string Code) {

            LoadingHole.IsRunning = true;

            try {
                await Task.Run(() => {
                    string userJSON = App.ObjectManager.Resolve<DataProvider>().ActivateAccount(Code);
                    User user = JsonConvert.DeserializeObject<User>(userJSON.Substring(0, userJSON.Length - 1));
                    App.RegistrateUserConfig(user);
                    App.Current.MainPage = new ProfilePage();
                });
            } catch (NoConnectionException) {
                await DisplayAlert("Error", "No Internet connection", "OK");
            } catch (InvalidCodeException) {
                await DisplayAlert("Activation failed", "Activation code was wrong", "OK");
            } finally {
                LoadingHole.IsRunning = false;
            }
        }

        protected override bool OnBackButtonPressed() => true;
    }
}