using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using XLabs.Forms;
using XLabs.Forms.Controls;

using TellMe.Model;

namespace TellMe.Pages
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class TestPage : ContentPage
    {
        private Lesson L { get; set; }
        private Progress P { get; set; }

        private LessonTask LT { get; set; }
        private int TestNum { get; set; }

        private Dictionary<Variant, CustomRadioButton> RBTable { get; set; }
        private Dictionary<Variant, CheckBox> CBTable { get; set; }

        public TestPage(Lesson L, Progress P, LessonTask LT, int TestNum)
        {
            this.L = L;
            this.P = P;

            this.LT = LT;
            this.TestNum = TestNum;

            InitializeComponent();

            Header.Text = L.name + ": Test " + TestNum;
            TestText.Text = LT.testText;

            Uri TestPictureUri;
            if(Uri.TryCreate(LT.testPicture, UriKind.Absolute, out TestPictureUri))
                TestPicture.Source = ImageSource.FromUri(TestPictureUri);

            if (LT.type == TestType.ONE_CORRECT.ToString())
                this.RBTable = LoadRB();
            else if (LT.type == TestType.SEVERAL_CORRECT.ToString())
                this.CBTable = LoadCB();
            else if (LT.type == TestType.WRITE_WORD.ToString())
                InputWordPanel.IsVisible = true;

            Check.Clicked += Check_Clicked;
        }

        private void Check_Clicked(object sender, EventArgs e)
        {
            if (LT.type == TestType.ONE_CORRECT.ToString())
                ProcessAnswer(RBTable.Where(T => T.Value.Checked).Select(T => T.Key).ToList());
            else if (LT.type == TestType.SEVERAL_CORRECT.ToString())
                ProcessAnswer(CBTable.Where(T => T.Value.Checked).Select(T => T.Key).ToList());
            else if (LT.type == TestType.WRITE_WORD.ToString())
                ProcessAnswer(InputWordEntry.Text);
        }

        private async Task<bool> ProcessAnswer(string Answer)
        {
            if(!P.Pass(false, LT, Answer)) {
                await DisplayAlert("Wrong", "Try again...", "OK");
                return false;
            } else {
                await DisplayAlert("Correct", "Good Job!!!", "OK").
                      ContinueWith(T => App.Current.MainPage = new LessonPage(L, P));
                return true;
            }
        }

        private async Task<bool> ProcessAnswer(List<Variant> Answers)
        {
            if (Answers.Count == 0) {
                await DisplayAlert("Wrong", "Try again...", "OK");
                return false;
            } else if (!P.Pass(false, LT, String.Join(" ", Answers.Select(A => A.number)))) {
                await DisplayAlert("Wrong", "Try again...", "OK");
                return false;
            } else {
                await DisplayAlert("Correct", "Good Job!!!", "OK").
                      ContinueWith(T => App.Current.MainPage = new LessonPage(L, P));
                return true;
            }
        }

        private Dictionary<Variant, CustomRadioButton> LoadRB()
        {
            Dictionary<Variant, CustomRadioButton> VTable = new Dictionary<Variant, CustomRadioButton>();

            foreach (Variant V in LT.variants)
            {
                CustomRadioButton RB = new CustomRadioButton();
                RB.Text = V.text;
                RB.Checked = false;
                RB.Style = (Style)Resources["TestRB"];
                VTable.Add(V, RB);

                RB.CheckedChanged = new EventHandler<XLabs.EventArgs<bool>>((sender, e) => {
                    if (!e.Value) return;

                    foreach (CustomRadioButton CRB in VTable.Values)
                        if (!CRB.Equals(sender))
                            CRB.Checked = false;
                });

                Tests.Children.Add(RB);
            }

            return VTable;
        }

        private Dictionary<Variant, CheckBox> LoadCB()
        {
            Dictionary<Variant, CheckBox> VTable = new Dictionary<Variant, CheckBox>();

            foreach (Variant V in LT.variants) {

                CheckBox CB = new CheckBox();
                CB.DefaultText = V.text;
                CB.Checked = false;
                CB.Style = (Style)Resources["TestCB"];
                VTable.Add(V, CB);

                Tests.Children.Add(CB);
            }

            return VTable;
        }

        //private 

        protected override bool OnBackButtonPressed()
        {
            App.Current.MainPage = new LessonPage(L, P);
            return true;
        }
    }
}