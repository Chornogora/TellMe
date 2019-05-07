import React, { Component } from 'react'
import SideBar from './SideBar';

class Menu extends Component {
    constructor() {
        super();
        this.state = {
          showReply: false,  
        };
      }

      onClickShowMenu(e) {
        e.preventDefault();
        this.setState({ showReply: !this.state.showReply });
      }

  render() {
    return (
        <div>
        <div onClick={this.onClickShowMenu.bind(this)}>
          <h1>Tell me(click here)</h1>
        </div>
        <div>{this.state.showReply && <SideBar />}</div>

        
      </div>
    )
  }
}

export default Menu;
