using System;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.IO;

using Autofac;

using Xamarin.Forms;

namespace TellMe.Server {

    public class NoConnectionException : Exception { }
    public class InitFailedException : Exception { }
    public class UserExistsException : Exception { }
    public class InvalidCodeException : Exception { }
    public class InvalidLoginException : Exception { }
    public class InvalidPasswordException : Exception { }
    public class UnAutorizedUserException : Exception { }
    public class NoSuchUserException : Exception { }

    public class DataProvider : IDataProvider {

        private static UTF8Encoding UTF8Enc = App.ObjectManager.Resolve<UTF8Encoding>();
        private static ASCIIEncoding ASCIIEnc = App.ObjectManager.Resolve<ASCIIEncoding>();
        
        private const string StrNull = "null";
        private const string QueryContentType = "application/x-www-form-urlencoded";

        //<INIT>
        private static readonly string InitPath = App.QueryPathes["Init", "Path"];
        private static readonly string InitMeth = App.QueryPathes["Init", "Method"];
        private const string InitOK = "OK";
        //</INIT>

        //<REGISTRATION>
        private static readonly string SignUpPath = App.QueryPathes["SignUp", "Path"];
        private static readonly string SignUpMeth = App.QueryPathes["SignUp", "Method"];
        private const string LoginKey = "login";
        private const string PasswordKey = "password";
        private const string EmailKey = "email";
        private const string BirthKey = "birthday";
        private const string LoginExistsResopnse = "Login exists";
        //</REGISTRATION>

        //<ACTIVATION>
        private static readonly string ActivatePath = App.QueryPathes["Activate", "Path"];
        private static readonly string ActivateMeth = App.QueryPathes["Activate", "Method"];
        private const string CodeKey = "code";
        private const string InvalidCodeResponse = "Invalid code";
        //</ACTIVATION>

        //<AUTORIZATION>
        private static readonly string LogInPath = App.QueryPathes["LogIn", "Path"];
        private static readonly string LogInMeth = App.QueryPathes["LogIn", "Method"];
        private const string InvalidLoginResponse = "Invalid login";
        private const string InvalidPasswordResponse = "Invalid password";
        //</AUTORIZATION>

        //<GET_USER>
        private static readonly string GetUserPath = App.QueryPathes["GetUser", "Path"];
        private static readonly string GetUserMeth = App.QueryPathes["GetUser", "Mathod"];
        private const string IdKey = "id";
        private const string InvalidIdResponse = "Invalid id";
        //</GET_USER>

        //<GET_USER>
        private static readonly string GetLevelsPath = App.QueryPathes["GetLevels", "Path"];
        private static readonly string GetLevelsMeth = App.QueryPathes["GetLevels", "Mathod"];
        //</GET_USER>

        public void Init() {

            string IsInitOK = Query(InitPath, InitMeth, "");

            if (IsInitOK != InitOK)
                throw new InitFailedException();
        }

        public void SignUp(string Login, string Password, string Email, DateTime Birth) {

            string birthday = Birth == null ? StrNull : $"{Birth.Year}-{Birth.Month}-{Birth.Day}";

            string dataString = $"{LoginKey}={Login}&{PasswordKey}={Password}&{EmailKey}={Email}&{BirthKey}={birthday}";
            string response = Query(SignUpPath, SignUpMeth, dataString);

            if (response.StartsWith(LoginExistsResopnse))
                throw new UserExistsException();
        }

        public string ActivateAccount(string Code) {

            string dataString = $"{CodeKey}={Code}";
            string response = Query(ActivatePath, ActivateMeth, dataString);

            if (response.StartsWith(InvalidCodeResponse))
                throw new InvalidCodeException();

            return response;
        }

        public string LogIn(string Login, string Password) {

            string dataString = $"{LoginKey}={Login}&{PasswordKey}={Password}";
            string response = Query(LogInPath, LogInMeth, dataString);

            if (response.StartsWith(InvalidLoginResponse))
                throw new InvalidLoginException();

            if (response.StartsWith(InvalidPasswordResponse))
                throw new InvalidPasswordException();

            return response;
        }

        public string GetUserInfo(int id) {

            string dataString = $"{IdKey}={id.ToString()}";
            string response = Query(GetUserPath, GetUserMeth, dataString);

            if (response.StartsWith(InvalidIdResponse))
                throw new NoSuchUserException();

            return response;
        }

        public string GetLevels() => Query(GetLevelsPath, GetLevelsMeth, "");

        private string Query(string path, string meth, string data) {
            try {
                return meth == WebRequestMethods.Http.Post ? QueryPOST(path, data) : QueryGET(path, data);
            } catch(WebException) {
                throw new NoConnectionException();
            }
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
