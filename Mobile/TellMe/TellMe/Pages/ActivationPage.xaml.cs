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

namespace TellMe
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class ActivationPage : ContentPage
    {
        public ActivationPage()
        {            
            InitializeComponent();
            
            ActivationButton.Clicked += ActivationButton_Clicked;
        }

        protected override void OnAppearing() => DisplayAlert("Registration successful!", "Please check your email for an activation code", "OK");

        private void ActivationButton_Clicked(object sender, EventArgs e) {

            if(Code.Text == null || Code.Text.Length != 5) {

                DisplayAlert("Activation failed", "Activation code is invalid", "OK");
                return;
            }

            ActivateAsync(Code.Text);
        }

        private async void ActivateAsync(string Code) {

            LoadingHole.IsRunning = true;

            try {

                await Task.Run(() => {

                    string userJSON = App.ObjectManager.Resolve<DataProvider>().ActivateAccount(Code);
                    User user = JsonConvert.DeserializeObject<User>(userJSON.Substring(0, userJSON.Length - 1));
                    App.RegistrateUserConfig(user);
                    App.Current.MainPage = App.ObjectManager.Resolve<ProfilePage>(new TypedParameter(typeof(User), user));
                });
            }
            catch (InvalidCodeException) {

                await DisplayAlert("Activation failed", "Activation code was wrong", "OK");
            }
            finally {

                LoadingHole.IsRunning = false;
            }
        }

        protected override bool OnBackButtonPressed() => true;
    }
}