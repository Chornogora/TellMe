import React, { Component } from "react";
import { ComponentTypeMenu } from "./ProfilePage";
// import SideBar from "../nav/SideBar";

class Profile extends Component {
  constructor(props) {
    super(props);
    this.state = {
      componentTypeMenu: "lectures"
    };
  }

  render() {
    return (
      <div>
         
        <div
          onClick={() =>
            this.props.onChangeComponentType(ComponentTypeMenu.lectures)
          }
        >
          <h1>Lectures(click here)</h1>
        </div>
        <div
          onClick={() =>
            this.props.onChangeComponentType(ComponentTypeMenu.profile)
          }
        >
          <h1>Profile(click here)</h1>
        </div>
        <div
          onClick={() =>
            this.props.onChangeComponentType(ComponentTypeMenu.tournaments)
          }
        >
          <h1>Tournaments(click here)</h1>
        </div>
      </div>
    );
  }
}

export default Profile;
