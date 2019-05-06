import React, { Component } from 'react';
import './ProfilePage';
import Input from '../../components/Input';
import Submit from '../../components/Submit';
import { ComponentType } from './ProfilePage'
import profile from '../../images/profile.jpg';

class EditProfilePage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            nickname: '',
            birthday: '',
            photo: 'Remember Me'

        };

        this.handleNicknameChange = this.handleNicknameChange.bind(this);
        this.handleBirthdayChange = this.handleBirthdayChange.bind(this);
        this.handlePhoto = this.handlePhoto.bind(this);
    }

    handleNicknameChange(e) {
        this.setState({ nickname: e.target.value })
    }
    handleBirthdayChange(e) {
        this.setState({ birthday: e.target.value })
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                {this.state.error &&
                    <span className="error-message">{this.state.error}</span>
                }

                <Input type={'text'}
                    title={'Nickname'}
                    name={'nickname'}
                    value={this.state.nickname}
                    placeholder={'Enter your nickname'}
                    handleChange={this.handleNicknameChange}
                />
               
               <img src={profile} width="100" height="50" />


            </form>
        )
    }



}
