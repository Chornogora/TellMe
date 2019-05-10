using System;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.IO;

using Xamarin.Forms;

namespace TellMe.Server {

    public class InitFailedException : Exception { }
    public class UserExistsException : Exception { }
    public class InvalidCodeException : Exception { }
    public class InvalidLoginException : Exception { }
    public class InvalidPasswordException : Exception { }


    public class DataProvider : IDataProvider {

        private static UTF8Encoding UTF8Enc = DependencyService.Get<UTF8Encoding>();
        private static ASCIIEncoding ASCIIEnc = DependencyService.Get<ASCIIEncoding>();
        private static Config Configuration = DependencyService.Get<Config>();
        
        private const string StrNull = "null";
        private const string QueryContentType = "application/x-www-form-urlencoded";

        //<INIT>
        private static readonly string InitPath = Configuration["Init", "Path"];
        private const string InitOK = "OK";
        //</INIT>

        //<REGISTRATION>
        private static readonly string SignUpPath = Configuration["SignUp", "Path"];
        private const string LoginKey = "login";
        private const string PasswordKey = "password";
        private const string EmailKey = "email";
        private const string BirthKey = "birthday";
        private const string LoginExistsResopnse = "Login exists";
        //</REGISTRATION>

        //<ACTIVATION>
        private static readonly string ActivatePath = Configuration["Activate", "Path"];
        private const string CodeKey = "code";
        private const string InvalidCodeResponse = "Invalid code";
        //</ACTIVATION>

        //<AUTORIZATION>
        private static readonly string LogInPath = Configuration["LogIn", "Path"];
        private const string InvalidLoginResponse = "Invalid login";
        private const string InvalidPasswordResponse = "Invalid password";
        //</AUTORIZATION>

        public void Init() {

            string IsInitOK = QueryGET(InitPath, "");

            if (IsInitOK != InitOK)
                throw new InitFailedException();
        }

        public void SignUp(string Login, string Password, string Email, DateTime Birth) {

            string birthday = Birth == null ? StrNull : $"{Birth.Year}-{Birth.Month}-{Birth.Day}";

            string dataString = $"{LoginKey}={Login}&{PasswordKey}={Password}&{EmailKey}={Email}&{BirthKey}={birthday}";
            string response = QueryPOST(SignUpPath, dataString);

            if (response.StartsWith(LoginExistsResopnse))
                throw new UserExistsException();
        }

        public void ActivateAccount(string Code) {

            string dataString = $"{CodeKey}={Code}";
            string response = QueryPOST(ActivatePath, dataString);

            if (response.StartsWith(InvalidCodeResponse))
                throw new InvalidCodeException();
        }

        public void LogIn(string Login, string Password) {

            string dataString = $"{LoginKey}={Login}&{PasswordKey}={Password}";
            string response = QueryPOST(LogInPath, dataString);

            if (response.StartsWith(InvalidLoginResponse))
                throw new InvalidLoginException();

            if (response.StartsWith(InvalidPasswordResponse))
                throw new InvalidPasswordException();
        }

        private string QueryPOST(string path, string dataString) {

            byte[] data = UTF8Enc.GetBytes(dataString);

            HttpWebRequest request = HttpWebRequest.Create(path) as HttpWebRequest;
            request.Method = WebRequestMethods.Http.Post;
            request.ContentType = QueryContentType;
            request.ContentLength = data.Length;

            using (Stream requestStream = request.GetRequestStream())
                requestStream.Write(data, 0, data.Length);

            List<byte> responseData = new List<byte>();

            using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
            using (Stream responseStream = response.GetResponseStream())
                for (int i = 0; i != -1; i = responseStream.ReadByte(), responseData.Add((byte)i)) ;

            return UTF8Enc.GetString(responseData.ToArray());
        }

        private string QueryGET(string path, string dataString) {

            HttpWebRequest request = HttpWebRequest.Create($"{path}?{dataString}") as HttpWebRequest;
            string responseData = "";

            using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
            using (StreamReader responseStream = new StreamReader(response.GetResponseStream()))
                responseData = responseStream.ReadToEnd();

            return responseData;
        }
    }
}
