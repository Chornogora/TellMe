import React, { Component } from "react";
//import "./Login.css";
import Login from "./Login";
import SignUp from "./SignUp";
import ForgetPassword from "./ForgetPassword";
import AuthorizationPage from "./AuthorizationPage";
import LecturePage from "../lectures/LecturePage";
// import SideBar from "../nav/SideBar";
// import { Container } from "semantic-ui-react";
// import EventDashboard from "../../events/EventDashboard";
// import EditeProfileForm from "../profile/EditeProfileForm";
// import PhotosPage from "../profile/PhotosPage";

export const ComponentType = {
  login: 1,
  signUp: 2,
  forgetPassword: 3,
  authorization: 4
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
          {
            <LecturePage/>
            /* <SideBar />
          <Container className="main">
          <EditeProfileForm/>
          <PhotosPage/>
          </Container> */}
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
    }
  }
}

export default LoginPage;
