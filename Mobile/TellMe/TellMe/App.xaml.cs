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

namespace TellMe
{
    public partial class App : Application {

        public static IContainer ObjectManager { get; private set; }
        public static ConfigurationManager QueryPathes { get; private set; }
        public static FileManager UserConfig { get; private set; }

        private void InitServices() {

            ContainerBuilder builder = new ContainerBuilder();

            builder.RegisterType<DataProvider>().SingleInstance();

            builder.RegisterType<ASCIIEncoding>().SingleInstance();
            builder.RegisterType<UTF8Encoding>().SingleInstance();

            builder.RegisterType<HelloPage>().SingleInstance();
            builder.RegisterType<SignUpPage>().SingleInstance();
            builder.RegisterType<LogInPage>().SingleInstance();
            builder.RegisterType<ActivationPage>().SingleInstance();
            builder.RegisterType<ProfilePage>().UsingConstructor(typeof(User)).SingleInstance();
            builder.RegisterType<LessonsPage>().SingleInstance();

            ObjectManager = builder.Build();

            QueryPathes = new ConfigurationManager("Pathes.json");

            UserConfig = new FileManager(Environment.SpecialFolder.Personal, "UserData.json");
            UserConfig.CreateFileIfNotExists();
        }

        public static void RegistrateUserConfig(User user) {

            UserConfig.WriteFile(JsonConvert.SerializeObject(new UserCache(user)));
        }

        public static void Close() {

            Android.OS.Process.KillProcess(Android.OS.Process.MyPid());
        }

        public App() {

            Thread Init = new Thread(InitServices);
            Init.Start();

            InitializeComponent();

            Init.Join();

            string UserCacheJson = UserConfig.ReadFile();

            if (UserCacheJson == "")
                this.MainPage = App.ObjectManager.Resolve<HelloPage>();
            else {

                try {

                    UserCache UserCache = JsonConvert.DeserializeObject<UserCache>(UserCacheJson);
                    TypedParameter id = new TypedParameter(typeof(User), ObjectManager.Resolve<DataProvider>().GetUserInfo(UserCache.id));
                    this.MainPage = ObjectManager.Resolve<ProfilePage>(id);
                }
                catch (NotImplementedException ex) {

                    this.MainPage = App.ObjectManager.Resolve<HelloPage>();
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
