using System;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

using Android.App;
using Android.Content.PM;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;

using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;

using TellMe.Server;

namespace TellMe.Droid {

    [Activity(Label = "TellMe", Icon = "@mipmap/icon", Theme = "@style/MainTheme", MainLauncher = true, ConfigurationChanges = ConfigChanges.ScreenSize | ConfigChanges.Orientation)]
    public class MainActivity : FormsAppCompatActivity
    {

        private void InitServices() {

            DependencyService.Register<DataProvider>();
            DependencyService.Register<ASCIIEncoding>();
            DependencyService.Register<UTF8Encoding>();

            DependencyService.Register<HelloPage>();
            DependencyService.Register<SignUpPage>();
            DependencyService.Register<LogInPage>();
            DependencyService.Register<ActivationPage>();
            DependencyService.Register<LessonsPage>();

            DependencyService.Register<Config>();
        }

        protected override void OnCreate(Bundle savedInstanceState) {

            Thread Init = new Thread(InitServices);
            Init.Start();

            TabLayoutResource = Resource.Layout.Tabbar;
            ToolbarResource = Resource.Layout.Toolbar;

            base.OnCreate(savedInstanceState);
            
            Xamarin.Essentials.Platform.Init(this, savedInstanceState);
            Xamarin.Forms.Forms.Init(this, savedInstanceState);

            LoadApplication(new App());
        }

        public override void OnRequestPermissionsResult(int requestCode, string[] permissions, [GeneratedEnum] Android.Content.PM.Permission[] grantResults)
        {

            Xamarin.Essentials.Platform.OnRequestPermissionsResult(requestCode, permissions, grantResults);
            base.OnRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}