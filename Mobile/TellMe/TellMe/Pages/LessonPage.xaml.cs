using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using TellMe.Model;
using TellMe.Server;

namespace TellMe.Pages
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class LessonPage : ContentPage
    {
        private Lesson L { get; set; }
        private Progress P { get; set; }
        
        public LessonPage(Lesson L, Progress P)
        {
            this.L = L;
            this.P = P;

            L.LoadTasks();
            
            InitializeComponent();
            this.Appearing += LessonPage_Appearing;
        }

        private void LessonPage_Appearing(object sender, EventArgs e) {

            LessonName.Text = L.name;

            int Row = 0;
            int Column = 0;
            int CounterTests = 0;
            int CounterGrammars = 0;
            int CounterLexics = 0;

            foreach (LessonTask LT in L) {

                BoxView TaskProgressIndicator = new BoxView();
                bool Passed = LT.number <= P.taskPassedNumber;
                bool Locked = LT.number > P.taskPassedNumber + 1;

                TaskProgressIndicator.Style = (Style)(Resources[Passed ? "PassedTaskIndicator" : (Locked ? "LockedTaskIndicator" : "NotPassedTaskIndicator")]);
                LessonProgressIndicatorsGrid.Children.Add(TaskProgressIndicator, Column, Row);

                if (++Column == Constants.INLINE_INDICATORS_COUNT) {
                    Row++;
                    Column = 0;
                }

                Button TaskButton = new Button();
                TaskButton.Style = (Style)(Resources[Passed ? "PassedTask" : (Locked ? "LockedTask" : "NotPassedTask")]);

                if(LT.IsTest()) {
                    int TC = ++CounterTests;
                    TaskButton.Text = $"Test #{TC}";
                    TaskButton.Clicked += (ctx, arg) => { if (!Locked) App.Current.MainPage = new TestPage(L, P, LT, TC); };
                }
                else if(LT.IsGrammar()) {
                    int GC = ++CounterGrammars;
                    TaskButton.Text = $"Grammar #{GC}";
                    TaskButton.Clicked += (ctx, arg) => { if (!Locked) App.Current.MainPage = new GrammarPage(L, P, LT, GC); };
                }
                else if(LT.IsLexical()) {
                    int LC = ++CounterLexics;
                    TaskButton.Text = $"Lexics #{LC}";
                    TaskButton.Clicked += (ctx, arg) => { if (!Locked) App.Current.MainPage = new LexicalPage(L, P, LT, LC); };
                }

                TasksList.Children.Add(TaskButton);
            }
        }

        protected override bool OnBackButtonPressed()
        {
            App.Current.MainPage = new LessonListPage();
            return true;
        }
    }
}