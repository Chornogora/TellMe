using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using TellMe.Server;

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

        private void ActivationButton_Clicked(object sender, EventArgs e) => ActivateAsync(Code.Text);

        private async void ActivateAsync(string Code) {

            LoadingHole.IsRunning = true;

            try {

                await Task.Run(() => {

                    DependencyService.Get<DataProvider>().ActivateAccount(Code);
                    App.Current.MainPage = DependencyService.Get<HelloPage>();
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