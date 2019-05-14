using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

using Microsoft.Extensions.Primitives;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.FileProviders;
using Microsoft.Extensions.Configuration.Json;

using Xamarin.Forms;

using Android.Content.Res;

namespace TellMe {
    public class ConfigurationManager : IDisposable {

        private JsonConfigurationProvider JCP = new JsonConfigurationProvider(new JsonConfigurationSource());
        private AssetManager assets = Android.App.Application.Context.Assets;

        public string this[params string[] keys] {

            get {

                JCP.TryGet(String.Join(":", keys), out string temp);
                return temp;
            }
        }

        public ConfigurationManager(string filename) {

            Stream asset = Android.App.Application.Context.Assets.Open(filename);
            this.JCP.Load(asset);
            asset.Close();
        }

        public void Dispose() => assets.Dispose();
    }
}