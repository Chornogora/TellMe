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
    public partial class LexicalPage : ContentPage
    {
        private Lesson L { get; set; }
        private Progress P { get; set; }

        private LessonTask LT { get; set; }
        private int GrammarNum { get; set; }

        public LexicalPage(Lesson L, Progress P, LessonTask LT, int LexicalNum)
        {
            this.L = L;
            this.P = P;

            this.LT = LT;
            this.GrammarNum = GrammarNum;

            InitializeComponent();

            Header.Text = L.name + ": Lexics " + LexicalNum;

            int H = 1;
            foreach(Word W in LT.words)
            {
                Label EWord = new Label();
                EWord.Text = W.name;
                EWord.Style = (Style)Resources["EngWord"];

                Label RWord = new Label();
                RWord.Text = W.translation;
                RWord.Style = (Style)Resources["RusWord"];

                Label Description = new Label();
                Description.Text = W.description;
                Description.Style = (Style)Resources["Desciption"];

                Words.Children.Add(EWord, 0, H);
                Words.Children.Add(RWord, 1, H);
                Words.Children.Add(Description, 2, H++);
            }

            P.Pass(true, LT.number);
        }

        protected override bool OnBackButtonPressed()
        {
            App.Current.MainPage = new LessonPage(L, P);
            return true;
        }
    }
}