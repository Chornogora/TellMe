import React, { Component } from 'react';
import './Login.css';
import Login from './Login';
import SignUp from './SignUp';
import ForgetPassword from './ForgetPassword';

export const ComponentType = { "login": 1, "signUp": 2, "forgetPassword": 3 }
Object.freeze(ComponentType);

class ProfilePage extends Component {
  state = { componentType: ComponentType.forgetPassword };

  changeComponentType = (componentType) => {
    this.setState({ ...this.state, componentType });
  }

  render() {
    
    if (this.state.componentType === ComponentType.login) {
      return (
        <Login onChangeComponentType={this.changeComponentType}/>
      )
    }
    else if (this.state.componentType === ComponentType.signUp) {
      return (
        <SignUp onChangeComponentType={this.changeComponentType}/>
      )
    }
    else if (this.state.componentType === ComponentType.forgetPassword) {
      return (
        <ForgetPassword  onChangeComponentType={this.changeComponentType}/>
      )
    }
  
}
}

export default ProfilePage;
