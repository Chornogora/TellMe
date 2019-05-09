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
    public partial class SignUpPage : ContentPage
    {
        public SignUpPage()
        {            
            InitializeComponent();

            ReturnButton.Clicked += ReturnButton_Clicked;
            SignUpButton.Clicked += SignUpButton_Clicked;
        }

        private void SignUpButton_Clicked(object sender, EventArgs e) {

            if (Password.Text != PasswordConfirm.Text) {

                DisplayAlert("Registration failed", "Your passwords don't match", "Fuck");
                return;
            }

            try
            {
                DependencyService.Get<DataProvider>().SignUp(Login.Text, Password.Text, Email.Text, Birth.Date);
                DisplayAlert("Registration successful!", "Please check your email for an activation code", "OK");
                App.Current.MainPage = DependencyService.Get<ActivationPage>();
            }
            catch (UserExistsException)
            {
                DisplayAlert("Registration failed", "User with such login already exists", "Fuck");
            }
        }

        private void ReturnButton_Clicked(object sender, EventArgs e) => GoBack();

        protected override bool OnBackButtonPressed() {

            GoBack();
            return true;
        }

        private void GoBack() => App.Current.MainPage = DependencyService.Get<HelloPage>();

        protected override void OnSizeAllocated(double width, double height) {

            base.OnSizeAllocated(width, height);

            if(width > height) 
                HeaderGrid.IsVisible = false;
            else
                HeaderGrid.IsVisible = true;
        }
    }
}