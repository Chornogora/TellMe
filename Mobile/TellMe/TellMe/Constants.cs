using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace TellMe
{
    public delegate bool Validator();
    public delegate Task DisplayMethod(string Header, string Text, string Close);

    public class Alert {
        public string Header { get; private set; }
        public string Text { get; private set; }
        public string Close { get; private set; }

        private DisplayMethod displayHandler;

        public Alert(string Header, string Text, string Close, DisplayMethod DisplayHandler) {
            this.Header = Header;
            this.Text = Text;
            this.Close = Close;
            this.displayHandler = DisplayHandler;
        }

        public Task Display() => displayHandler(Header, Text, Close);
    }

    public class Constants {

        public const int SESSION_LIFETIME_MINUTES = 60;
        public const string AVATAR_STORAGE_PATH = "http://192.168.43.36:5000/";

        public static Color ValidColor = Color.Green;
        public static Color InvalidColor = Color.Red;

        public static Action<Entry> DefaultInvalidHandler = E => E.BackgroundColor = InvalidColor;
        public static Action<Entry> DefaultValidHandler = E => E.BackgroundColor = ValidColor;

        public static Regex LoginPattern = new Regex("^[0-9a-zA-Z]{3,20}$");
        public static Regex PasswordPattern = new Regex("^[0-9a-zA-Z]{6,12}$");
        public static Regex EmailPattern = new Regex("^[0-9a-zA-Z_\\.]+@[a-z0-9\\-\\.]+$");

        public static List<T> GetAllInvalid<T>(Dictionary<T, Validator> Validators) where T : new() {
            List<T> Invalids = new List<T>();
            foreach (KeyValuePair<T, Validator> Validator in Validators)
                if (!Validator.Value())
                    Invalids.Add(Validator.Key);
            return Invalids;
        }

        public static List<T> Substract<T>(List<T> Super, List<T> Sub) {
            List<T> Substraction = new List<T>();
            foreach (T item in Super)
                if (!Sub.Contains(item))
                    Substraction.Add(item);
            return Substraction;
        }

        public static bool Validate<T>(List<T> Inputs, Dictionary<T, Validator> Validators,
                                       Dictionary<T, Alert> Alerts, Action<T> InvalidHandle, 
                                       Action<T> ValidHandle, bool Messages) where T : new() {
            List<T> Invalids = GetAllInvalid<T>(Validators);
            List<T> Valids = Substract<T>(Inputs, Invalids);

            foreach (T Invalid in Invalids) {
                InvalidHandle(Invalid);
                if (Messages)
                    Alerts[Invalid].Display();
            }

            foreach (T Valid in Valids)
                ValidHandle(Valid);

            return Invalids.Count == 0;
        }

        public static void ApplyTextChangedHandler(List<Entry> Inputs, EventHandler<TextChangedEventArgs> Handler) {
            foreach (Entry input in Inputs)
                input.TextChanged += Handler;
        }

        public class Enumerator<T> : IEnumerator<T> {
            private List<T> Items;
            private int index = -1;

            public Enumerator(List<T> Items) => this.Items = Items;
            public T Current => Items[index];
            object IEnumerator.Current => this.Current;
            public bool MoveNext() => ++index < Items.Count;
            public void Dispose() { }
            public void Reset() => index = -1;
        }
    }
}
