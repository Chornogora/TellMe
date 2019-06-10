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
    public class NoSuchLessonException : Exception { }
    public class NoSuchProgressException : Exception { }
    public class WrongAnswerException : Exception { }

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
        private static readonly string GetUserMeth = App.QueryPathes["GetUser", "Method"];
        private const string IdKey = "id";
        private const string InvalidIdResponse = "Invalid id";
        //</GET_USER>

        //<GET_LEVELS>
        private static readonly string GetLevelsPath = App.QueryPathes["GetLevels", "Path"];
        private static readonly string GetLevelsMeth = App.QueryPathes["GetLevels", "Method"];
        //</GET_LEVELS>

        //<GET_LESSONS>
        private static readonly string GetLessonsPath = App.QueryPathes["GetLessons", "Path"];
        private static readonly string GetLessonsMeth = App.QueryPathes["GetLessons", "Method"];
        //</GET_LESSONS>

        //<GET_PROGRESS>
        private static readonly string GetProgressPath = App.QueryPathes["GetProgress", "Path"];
        private static readonly string GetProgressMeth = App.QueryPathes["GetProgress", "Method"];
        private const string GetProgressIdKey = "userId";
        private const string GetProgressInvalidIdResponse = "Invalid id";
        //</GET_PROGRESS>

        //<GET_TASKS_COUNT>
        private static readonly string GetTasksCountPath = App.QueryPathes["GetTasksCount", "Path"];
        private static readonly string GetTasksCountMeth = App.QueryPathes["GetTasksCount", "Method"];
        private const string GetTasksCountIdKey = "id";
        private const string GetTasksCountInvalidIdResponse = "Invalid id";
        //</GET_TASKS_COUNT>

        //<GET_LESSON>
        private static readonly string GetLessonPath = App.QueryPathes["GetLesson", "Path"];
        private static readonly string GetLessonMeth = App.QueryPathes["GetLesson", "Method"];
        private const string GetLessonIdKey = "id";
        private const string GetLessonInvalidIdResponse = "Invalid id";
        //</GET_LESSON>

        //<GET_LESSON_TASKS>
        private static readonly string GetLessonTasksPath = App.QueryPathes["GetLessonTasks", "Path"];
        private static readonly string GetLessonTasksMeth = App.QueryPathes["GetLessonTasks", "Method"];
        private const string GetLessonTasksIdKey = "id";
        private const string GetLessonTasksInvalidIdResponse = "Invalid id";
        //</GET_LESSON_TASKS>

        //<PASS_THEORY>
        private static readonly string PassTheoryPath = App.QueryPathes["PassTheory", "Path"];
        private static readonly string PassTheoryMeth = App.QueryPathes["PassTheory", "Method"];
        private const string PassTheoryIdKey = "progressId";
        private const string PassTheoryInvalidIdResponse = "Invalid id";
        //</PASS_THEORY>

        //<PASS_TEST>
        private static readonly string PassTestPath = App.QueryPathes["PassTest", "Path"];
        private static readonly string PassTestMeth = App.QueryPathes["PassTest", "Method"];
        private const string PassTestIdKey = "progressId";
        private const string PassTestAnswerKey = "answer";
        private const string PassTestInvalidIdResponse = "Invalid id";
        private const string PassTestWrongAnswerResponse = "Failed";
        //</PASS_TEST>

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
        public string GetLessons() => Query(GetLessonsPath, GetLessonsMeth, "");

        public string GetProgress(int id) {

            string dataString = $"{GetProgressIdKey}={id.ToString()}";
            string response = Query(GetProgressPath, GetProgressMeth, dataString);

            if (response.StartsWith(GetProgressInvalidIdResponse))
                throw new NoSuchUserException();

            return response;
        }

        public int GetTasksCount(int id) {

            string dataString = $"{GetTasksCountIdKey}={id.ToString()}";
            string response = Query(GetTasksCountPath, GetTasksCountMeth, dataString);

            if (response.StartsWith(GetTasksCountInvalidIdResponse))
                throw new NoSuchLessonException();

            return Convert.ToInt32(response);
        }

        public string GetLesson(int id) {
            string dataString = $"{GetLessonIdKey}={id.ToString()}";
            string response = Query(GetLessonPath, GetLessonMeth, dataString);

            if (response.StartsWith(GetLessonInvalidIdResponse))
                throw new NoSuchLessonException();

            return response;
        }

        public string GetLessonTasks(int id)
        {
            string dataString = $"{GetLessonTasksIdKey}={id.ToString()}";
            string response = Query(GetLessonTasksPath, GetLessonTasksMeth, dataString);

            if (response.StartsWith(GetLessonTasksInvalidIdResponse))
                throw new NoSuchLessonException();

            return response;
        }

        public void PassTheory(int id)
        {
            string dataString = $"{PassTheoryIdKey}={id.ToString()}";
            string response = Query(PassTheoryPath, PassTheoryMeth, dataString);

            if (response.StartsWith(PassTheoryInvalidIdResponse))
                throw new NoSuchProgressException();
        }

        public void PassTest(int id, string answer)
        {
            string dataString = $"{PassTheoryIdKey}={id.ToString()}&{PassTestAnswerKey}={answer}";
            string response = Query(PassTestPath, PassTestMeth, dataString);

            if (response.StartsWith(PassTestInvalidIdResponse))
                throw new NoSuchProgressException();
            else if (response.StartsWith(PassTestWrongAnswerResponse))
                throw new WrongAnswerException();
        }

        private string Query(string path, string meth, string data) {
            try {
                return meth == WebRequestMethods.Http.Post ? QueryPOST(path, data) : QueryGET(path, data);
            } catch(WebException e) {
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
