import React, { Component } from "react";
//import "./Login.css";
import ProfilePage from "../profile/ProfilePage";
import LecturePage from "../lectures/LecturePage";
import TournamentPage from "../tournaments/TournamentPage";
import { ComponentTypeMenu } from "../profile/ProfilePage";

class SideBar extends Component {
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

export default SideBar;
