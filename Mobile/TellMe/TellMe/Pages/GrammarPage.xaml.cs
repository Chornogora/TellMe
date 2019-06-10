using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using TellMe.Model;

namespace TellMe.Pages
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class GrammarPage : ContentPage
    {
        private Lesson L { get; set; }
        private Progress P { get; set; }

        private LessonTask LT { get; set; }
        private int GrammarNum { get; set; }

        public GrammarPage(Lesson L, Progress P, LessonTask LT, int GrammarNum)
        {
            this.L = L;
            this.P = P;

            this.LT = LT;
            this.GrammarNum = GrammarNum;

            InitializeComponent();

            Header.Text = L.name + ": Grammar " + GrammarNum;
            GrammarText.Text = LT.content;

            if(!P.Pass(true, LT.number))
                DisplayAlert("Error", "No Internet connection", "OK");
        }

        protected override bool OnBackButtonPressed()
        {
            App.Current.MainPage = new LessonPage(L, P);
            return true;
        }
    }
}