import React, { Component } from "react";
import {
  //Icon,
  Menu,
  Button,
  Segment,
  Sidebar,
 // Header,
  //Image
} from "semantic-ui-react";

//import profile from "../../images/profile.jpg";

class SideBar extends Component {
  state = { activeItem: "home" };
  state = { visible: false };

  handleHideClick = () => this.setState({ visible: false });
  handleShowClick = () => this.setState({ visible: true });
  handleSidebarHide = () => this.setState({ visible: false });

  handleItemClick = (e, { name }) => this.setState({ activeItem: name });

  render() {
    const { activeItem } = this.state;
    const { visible } = this.state;

    return (
      <div>
        <Button.Group>
          <Button disabled={visible} onClick={this.handleShowClick}>
            Tell me (Show sidebar)
          </Button>
        </Button.Group>

        <Sidebar.Pushable as={Segment}>
          <Sidebar
            as={Menu}
            animation="overlay"
            icon="labeled"
            inverted
            onHide={this.handleSidebarHide}
            vertical
            visible={visible}
            width="thin"
          >
            <Menu inverted pointing vertical>
              <Menu.Item
                name="profile"
                active={activeItem === "profile"}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name="lectures"
                active={activeItem === "lectures"}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name="tournament"
                active={activeItem === "tournament"}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name="chats"
                active={activeItem === "chats"}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name="settings"
                active={activeItem === "settings"}
                onClick={this.handleItemClick}
              />
            </Menu>
          </Sidebar>

          <Sidebar.Pusher>
            <Segment basic>
            <Menu inverted pointing vertical>
              <Menu.Item
                name="prof(picture)"
                active={activeItem === "prof(picture)"}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name="lect"
                active={activeItem === "lect"}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name="tour"
                active={activeItem === "tour"}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name="cha"
                active={activeItem === "cha"}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name="sett"
                active={activeItem === "sett"}
                onClick={this.handleItemClick}
              />
            </Menu>
            </Segment>
          </Sidebar.Pusher>
        </Sidebar.Pushable>
      </div>
    );
  }
}

export default SideBar;
