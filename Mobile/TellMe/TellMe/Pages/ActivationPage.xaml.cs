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

        private void ActivationButton_Clicked(object sender, EventArgs e)
        {
            try
            {
                DependencyService.Get<DataProvider>().ActivateAccount(Code.Text);
                DisplayAlert("Activation successful", "Please, log in", "OK");
                App.Current.MainPage = DependencyService.Get<HelloPage>();
            }
            catch(InvalidCodeException)
            {
                DisplayAlert("Activation failed", "Activation code was wrong", "OK");
            }
        }

        protected override bool OnBackButtonPressed() => true;
    }
}