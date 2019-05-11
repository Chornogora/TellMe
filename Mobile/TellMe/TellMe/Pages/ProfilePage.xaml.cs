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

        private User user = null;
        public User CurrentUser {

            get { return user; }
            set { if (value != null) user = value; UserInfo.Text = value.ToString(); }
        }

        public ProfilePage() {

            InitializeComponent();
        }
    }
}