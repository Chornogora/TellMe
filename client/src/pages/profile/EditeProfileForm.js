import React, { Component } from "react";
import { Segment, Form, Button } from "semantic-ui-react";
import axios from 'axios';

class EditeProfileForm extends Component {
    constructor(props) {
        super(props);
        this.handleSignUpClick = this.handleSignUpClick.bind(this);
    }

    handleSignUpClick(login, password) {
   
        axios.post(`https://tellmeprod.herokuapp.com/authorization/authorize?login=${login}&password=${password}`)
        .then(response => 
            {
            console.log(response)});
    }

  render() {
    return (
      <Segment>
        <Form>
          <Form.Field>
            <label>Event Nickname</label>
            <input placeholder="Nickname" />
          </Form.Field>
          <Form.Field>
            <label>Event Birthday</label>
            <input type="date" placeholder="Birthday" />
          </Form.Field>
          <Form.Field>
            <label>City</label>
            <input placeholder="City" />
          </Form.Field>

          <Button positive type="submit">
            Load photo
          </Button>
          <Button type="button">Delete photo</Button>
          <Button positive type="submit">
            Submit
          </Button>
          <Button type="button">Cancel</Button>
        </Form>
      </Segment>
    );
  }
}

export default EditeProfileForm;
