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

        private IEnumerator<Word> WordsSequence { get; set; }

        public LexicalPage(Lesson L, Progress P, LessonTask LT, int LexicalNum)
        {
            this.L = L;
            this.P = P;

            this.LT = LT;
            this.GrammarNum = GrammarNum;
            WordsSequence = LT.words.GetEnumerator();

            InitializeComponent();

            Header.Text = L.name + ": Lexics " + LexicalNum;

            Next.Clicked += Next_Clicked; 
            if(!NextWord())
            {
                DisplayAlert("Sorry", "This task is out of words", "OK");
                return;
            }
        }

        private void Next_Clicked(object sender, EventArgs e)
        {
            if (!NextWord())
            {
                P.Pass(true, LT);
                DisplayAlert("You're making progress", "Well done!!!", "Continue").
                    ContinueWith(T => App.Current.MainPage = new LessonPage(L, P));
            }
        }

        private bool NextWord()
        {
            if (!WordsSequence.MoveNext())
                return false;

            WordName.Text = WordsSequence.Current.name;
            WordTranslation.Text = WordsSequence.Current.translation;
            WordDescription.Text = WordsSequence.Current.description;

            return true;
        }

        protected override bool OnBackButtonPressed()
        {
            App.Current.MainPage = new LessonPage(L, P);
            return true;
        }
    }
}