import React, { Component } from "react";
//import "./Login.css";
import Login from "./Login";
import SignUp from "./SignUp";
import ForgetPassword from "./ForgetPassword";
import AuthorizationPage from "./AuthorizationPage";
import LecturePage from "../lectures/LecturePage";
//import Menu from "../nav/Menu";
import ProfilePage from "../profile/ProfilePage";
import SideBar from "../nav/SideBar";
// import { Container } from "semantic-ui-react";
// import EventDashboard from "../../events/EventDashboard";
// import EditeProfileForm from "../profile/EditeProfileForm";
// import PhotosPage from "../profile/PhotosPage";

export const ComponentType = {
  login: 1,
  signUp: 2,
  forgetPassword: 3,
  authorization: 4,
  profile: 5
};
Object.freeze(ComponentType);

class LoginPage extends Component {
  state = { componentType: ComponentType.login };

  changeComponentType = componentType => {
    this.setState({ ...this.state, componentType });
  };

  render() {
    if (this.state.componentType === ComponentType.login) {
      return (
        <div>
          <Login onChangeComponentType={this.changeComponentType} />
        </div>
      );
    } else if (this.state.componentType === ComponentType.signUp) {
      return <SignUp onChangeComponentType={this.changeComponentType} />;
    } else if (this.state.componentType === ComponentType.forgetPassword) {
      return (
        <ForgetPassword onChangeComponentType={this.changeComponentType} />
      );
    } else if (this.state.componentType === ComponentType.authorization) {
    return <AuthorizationPage />;
    } else if (this.state.componentType === ComponentType.profile) {
      return <ProfilePage />;
    } 
  }
}

export default LoginPage;
