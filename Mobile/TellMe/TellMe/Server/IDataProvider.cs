using System;
using System.Collections.Generic;
using System.Text;

namespace TellMe.Server {

    public interface IDataProvider {

        void Init();
        void SignUp(string Login, string Password, string Email, DateTime Birth);
        string ActivateAccount(string code);
        string LogIn(string Login, string Password);
    }
}
