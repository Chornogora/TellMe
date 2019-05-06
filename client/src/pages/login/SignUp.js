import React, { Component } from "react";
import "./Login.css";
import Input from "../../components/Input";
import Submit from "../../components/Submit";
import { ComponentType } from "./LoginPage";
import axios from "axios";
//import AuthorizationPage from "./AuthorizationPage";

class SignUp extends Component {
  constructor(props) {
    super(props);

    this.state = {
      email: "",
      password: "",
      login: "",
      birthday: ""
    };

    this.handleEmailChange = this.handleEmailChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.handleLoginChange = this.handleLoginChange.bind(this);
    this.handleBirthdayChange = this.handleBirthdayChange.bind(this);
    this.handleSignUpClick = this.handleSignUpClick.bind(this);
  }

  handleEmailChange(e) {
    this.setState({ email: e.target.value });
  }
  handlePasswordChange(e) {
    this.setState({ password: e.target.value });
  }

  handleLoginChange(e) {
    this.setState({ login: e.target.value });
  }

  handleBirthdayChange(e) {
    this.setState({ birthday: e.target.value });
  }

  
  handleSignUpClick(login, password, email, birthday) {
    axios
      .post(
        
        `https://tellmeprod.herokuapp.com/registration/register?login=${login}&password=${password}&email=${email}&birthday=${birthday}`
      )
      
      .then(response => { 

        
        debugger;
        console.log(response);
      }).catch(r=>{
        debugger;
        console.log(r);
      });

      this.props.onChangeComponentType(ComponentType.authorization)
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        {this.state.error && (
          <span className="error-message">{this.state.error}</span>
        )}

        <div className="col-md-6">
          <h3>Create new account</h3>
          <h4>
            {" "}
            Do you have an account already?
            <Submit
              value={"Log in"}
              onClick={() =>
                this.props.onChangeComponentType(ComponentType.login)
              }
            />
          </h4>
        </div>

        <Input
          type={"text"}
          title={"Login"}
          name={"login"}
          value={this.state.login}
          placeholder={"Enter your login"}
          handleChange={this.handleLoginChange}
        />
        <Input
          type={"password"}
          title={"Password"}
          name={"password"}
          value={this.state.password}
          placeholder={"Enter your password"}
          handleChange={this.handlePasswordChange}
        />
        <Input
          type={"email"}
          title={"Email"}
          name={"email"}
          value={this.state.email}
          placeholder={"Enter your email"}
          handleChange={this.handleEmailChange}
        />
        <Input
          type={"date"}
          title={"Birthday"}
          name={"birthday"}
          value={this.state.birthday}
          placeholder={"Enter your birthday"}
          handleChange={this.handleBirthdayChange}
        />
        <Submit
          value={"Enter"}
          onClick={() =>
            this.handleSignUpClick(this.state.login, this.state.password, this.state.email, this.state.birthday)
          }
        />
      </form>
    );
  }
}

export default SignUp;
