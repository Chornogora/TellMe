import React, { Component } from 'react';
import './Login.css';
import Input from '../../components/Input';
import Submit from '../../components/Submit';
import{ComponentType} from './LoginPage'

class ForgetPassword extends Component {
    constructor(props) {
        super(props);

        this.state = {
            email: '',
            componentType: 'login'
        };

        this.handleEmailChange = this.handleEmailChange.bind(this);
        
    }

    handleEmailChange(e) {
        this.setState({ email: e.target.value })
    }

    render() {

        return (
            <form onSubmit={this.handleSubmit}>
                {this.state.error &&
                    <span className="error-message">{this.state.error}</span>
                }

                <div className="col-md-6">
                    <h3>Restore password</h3>
                </div>

                <Input type={'email'}
                    title={'Email'}
                    name={'email'}
                    value={this.state.email}
                    placeholder={'Enter your email'}
                    handleChange={this.handleEmailChange}
                />
                <Submit
                    value={'Restore'}
                />
                <Submit
                    value={'Come back'}
                    onClick = {()=>this.props.onChangeComponentType(ComponentType.login)}
                    
                    // changeComponentType = (componentType) => {
                    //     this.setState({ ...this.state, componentType });
                    //   }
                />
            </form>
        )
    }
}

export default ForgetPassword;