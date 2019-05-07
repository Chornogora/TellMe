import React, { Component } from "react";
import Input from "../../components/Input";
import Submit from "../../components/Submit";
import axios from "axios";
//         import Cookies from "universal-cookie";
// import { useCookies } from "react-cookie";

class AuthorizationPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      code: ""
    };

    this.handleCodeChange = this.handleCodeChange.bind(this);
    this.handleAuthorizationClick = this.handleAuthorizationClick.bind(this);
  }

  handleCodeChange(e) {
    this.setState({ code: e.target.value });
  }

  handleAuthorizationClick(code) {
    axios
      .post(
        `https://tellmeprod.herokuapp.com/registration/confirm?code=${code}`
      )
      .then(response => {
        debugger;
        console.log(response);
        //         import Cookies from "universal-cookie";
        // import { useCookies } from "react-cookie";
      })
      .catch(r => {
        debugger;
        console.log(r);
      });
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        {this.state.error && (
          <span className="error-message">{this.state.error}</span>
        )}

        <Input
          type={"text"}
          title={"Code"}
          name={"code"}
          value={this.state.code}
          placeholder={"Enter your code"}
          handleChange={this.handleCodeChange}
        />

        <Submit
          value={"Enter"}
          onClick={() => this.handleAuthorizationClick(this.state.code)}
        />
      </form>
    );
  }
}

export default AuthorizationPage;
