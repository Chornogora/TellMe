using System;
using System.Text;
using System.Threading;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using Autofac;
using Autofac.Core;

using Android.Content.Res;

using TellMe.Server;
using TellMe.Model;

using Newtonsoft.Json;
using TellMe.Pages;

namespace TellMe
{
    public partial class App : Application {

        public static IContainer ObjectManager { get; private set; }
        public static ConfigurationManager QueryPathes { get; private set; }
        public static FileManager UserConfig { get; private set; }
        public static UserCache CurrentUser { get; private set; }

        private void InitServices() {

            ContainerBuilder builder = new ContainerBuilder();

            builder.RegisterType<DataProvider>().SingleInstance();

            builder.RegisterType<ASCIIEncoding>().SingleInstance();
            builder.RegisterType<UTF8Encoding>().SingleInstance();

            builder.RegisterType<HelloPage>().SingleInstance();
            builder.RegisterType<SignUpPage>().SingleInstance();
            builder.RegisterType<LogInPage>().SingleInstance();
            builder.RegisterType<ActivationPage>().SingleInstance();

            ObjectManager = builder.Build();
            QueryPathes = new ConfigurationManager("Pathes.json");

            UserConfig = new FileManager(Environment.SpecialFolder.Personal, "UserData.json");
            UserConfig.CreateFileIfNotExists();

            Common.Load();
        }

        public static void RegistrateUserConfig(User user) {
            CurrentUser = new UserCache(user);
            UserConfig.WriteFile(JsonConvert.SerializeObject(CurrentUser));
        }
        public static void Close() => Android.OS.Process.KillProcess(Android.OS.Process.MyPid());

        public App() {

            Thread Init = new Thread(InitServices);
            Init.Start();

            InitializeComponent();

            Init.Join();

            string UserCacheJson = UserConfig.ReadFile();

            if (UserCacheJson == "")
                this.MainPage = App.ObjectManager.Resolve<HelloPage>();
            else {
                UserCache CachedUser = JsonConvert.DeserializeObject<UserCache>(UserCacheJson);
                if ((DateTime.Now - CachedUser.lastLogin).TotalMinutes >= Constants.SESSION_LIFETIME_MINUTES)
                    this.MainPage = ObjectManager.Resolve<HelloPage>();
                else {
                    CurrentUser = CachedUser;
                    this.MainPage = new ProfilePage();
                }
            }
        }

        protected override void OnStart() {
            

        }

        protected override void OnSleep() {

            // Handle when your app sleeps
        }

        protected override void OnResume() {

            // Handle when your app resumes
        }
    }
}
