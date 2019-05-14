using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using TellMe.Model;

namespace TellMe {

    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class ProfilePage : ContentPage {

        public User CurrentUser { get; private set; }

        public ProfilePage(User user) {

            CurrentUser = user;
            InitializeComponent();
        }
    }
}