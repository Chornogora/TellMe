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

using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.FileProviders;
using Microsoft.Extensions.Configuration.Json;

using Xamarin.Forms;
using Microsoft.Extensions.Primitives;
using Android.Content.Res;

namespace TellMe
{
    public class Config {

        public string this[string key]
        {
            get
            {
                string temp = "";
                JCP.TryGet(key, out temp);
                return temp;
            }
        }

        private static JsonConfigurationSource JCS = new JsonConfigurationSource();
        private static JsonConfigurationProvider JCP = new JsonConfigurationProvider(JCS);

        static Config()
        {
            AssetManager assets = Android.App.Application.Context.Assets;
            Stream asset = Android.App.Application.Context.Assets.Open("Pathes.json");

            JCP.Load(asset);
        }
    }
}