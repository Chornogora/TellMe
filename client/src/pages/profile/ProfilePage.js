import React, { Component } from "react";
import LecturePage from "../lectures/LecturePage";
import TournamentPage from "../tournaments/TournamentPage";
import Profile from "./Profile";

export const ComponentTypeMenu = {
  profile: 1,
  lectures: 2,
  tournaments: 3
};
Object.freeze(ComponentTypeMenu);

class ProfilePage extends Component {
  state = { componentTypeMenu: ComponentTypeMenu.profile };

  changeComponentTypeMenu = componentTypeMenu => {
    this.setState({ ...this.state, componentTypeMenu });
  };

  render() {
    if (this.state.componentTypeMenu === ComponentTypeMenu.profile) {
      return (
        <div>
          <Profile onChangeComponentType={this.changeComponentTypeMenu} />
        </div>
      );
    } else if (this.state.componentTypeMenu === ComponentTypeMenu.lectures) {
      return <LecturePage onChangeComponentType={this.changeComponentTypeMenu} />;
    } else if (this.state.componentTypeMenu === ComponentTypeMenu.tournaments) {
      return (
        <TournamentPage onChangeComponentType={this.changeComponentTypeMenu} />
      );
    }
  }
}

export default ProfilePage;