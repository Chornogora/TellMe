using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using Autofac;

namespace TellMe
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class LessonsPage : ContentPage
    {
        public LessonsPage()
        {
            InitializeComponent();
        }

        protected override bool OnBackButtonPressed()
        {
            App.Current.MainPage = App.ObjectManager.Resolve<HelloPage>();
            return true;
        }
    }
}