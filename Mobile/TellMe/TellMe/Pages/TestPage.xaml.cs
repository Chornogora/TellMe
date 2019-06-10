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

        private Dictionary<Variant, CustomRadioButton> Table { get; set; }

        public TestPage(Lesson L, Progress P, LessonTask LT, int TestNum)
        {
            this.L = L;
            this.P = P;

            this.LT = LT;
            this.TestNum = TestNum;

            InitializeComponent();

            Header.Text = L.name + ": Test " + TestNum;
            TestText.Text = LT.testText;
            this.Table = LoadRB();

            Check.Clicked += Check_Clicked;
        }

        private void Check_Clicked(object sender, EventArgs e)
        {
            List<KeyValuePair<Variant, CustomRadioButton>> VC = Table.Where(T => T.Value.Checked).ToList();

            if (VC.Count == 0) {
                DisplayAlert("Wrong", "Try again...", "OK");
                return;
            }

            if (!P.Pass(false, LT.number, String.Join(" ", VC.Select(V => V.Key.number))))
            {
                DisplayAlert("Wrong", "Try again...", "OK");
                return;
            }
            else
            {
                DisplayAlert("Correct", "Good Job!!!", "OK");
                App.Current.MainPage = new LessonPage(L, P);
            }
        }

        private Dictionary<Variant, CustomRadioButton> LoadRB()
        {
            Dictionary<Variant, CustomRadioButton> VTable = new Dictionary<Variant, CustomRadioButton>();

            foreach (Variant V in LT.variants)
            {
                CustomRadioButton Var = new CustomRadioButton();
                Var.Text = V.text;
                Var.Checked = false;
                Var.Style = (Style)Resources["TestRB"];
                VTable.Add(V, Var);
                Var.CheckedChanged = new EventHandler<XLabs.EventArgs<bool>>((sender, e) =>
                {
                    if (!e.Value) return;

                    foreach (CustomRadioButton CRB in VTable.Values)
                        if (!CRB.Equals(sender))
                            CRB.Checked = false;
                });
                Tests.Children.Add(Var);
            }

            return VTable;
        }

        protected override bool OnBackButtonPressed()
        {
            App.Current.MainPage = new LessonPage(L, P);
            return true;
        }
    }
}