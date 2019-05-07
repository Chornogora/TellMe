import React, { Component } from "react";
import axios from "axios";
import "./Login.css";
import Input from "../../components/Input";
import Submit from "../../components/Submit";
import CheckBox from "../../components/CheckBox";
import { ComponentType } from "./LoginPage";

class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      login: "",
      password: "",
      rememberPassword: "Remember Me",
      rememberOrNot: [],
      componentType: "signUp",
      componentForgotPassword: "forgetPassword"
    };

    this.handleLoginChange = this.handleLoginChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.handleCheckBox = this.handleCheckBox.bind(this);
    this.handleLoginClick = this.handleLoginClick.bind(this);
  }

  handleLoginChange(e) {
    this.setState({ login: e.target.value });
  }
  handlePasswordChange(e) {
    this.setState({ password: e.target.value });
  }
  handleCheckBox(e) {
    const newSelection = e.target.value;
    let newSelectionArray;

    if (this.state.rememberOrNot.indexOf(newSelection) > -1) {
      newSelectionArray = this.state.rememberOrNot.filter(
        s => s !== newSelection
      );
    } else {
      newSelectionArray = [...this.state.rememberOrNot, newSelection];
    }

    this.setState(prevState => ({
      ...prevState,
      rememberOrNot: newSelectionArray
    }));
  }

  handleLoginClick(login, password) {
    axios
      .post(
        `https://tellmeprod.herokuapp.com/authorization/authorize?login=${login}&password=${password}`
      )
      .then(response => {
        console.log(response);
      });
      this.props.onChangeComponentType(ComponentType.profile)
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        {this.state.error && (
          <span className="error-message">{this.state.error}</span>
        )}

        <div className="col-md-6">
          <h3>Log in</h3>
          <h4>
            {" "}
            Don`t you have an account yet?
            <Submit
              value={"Sign up"}
              onClick={() =>
                this.props.onChangeComponentType(ComponentType.signUp)
              }

              // changeComponentType = (componentType) => {
              //     this.setState({ ...this.state, componentType });
              //   }
            />
          </h4>
        </div>

        <Input
          type={"text"}
          title={"login"}
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
        <CheckBox
          name={"rememberOrNot"}
          option={this.state.rememberPassword}
          selectedOptions={this.state.rememberOrNot}
          handleChange={this.handleCheckBox}
        />
        <Submit
          value={"Forgot password?"}
          onClick={() =>
            this.props.onChangeComponentType(ComponentType.forgetPassword)
          }
        />
        <Submit
          value={"Enter"}
          onClick={() =>
            this.handleLoginClick(this.state.login, this.state.password)
          }
        />
      </form>
    );
  }
}

export default Login;
