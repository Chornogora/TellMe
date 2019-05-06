import React, { Component } from "react";
import FirstSectionLecture from "./FifthSectionLection";
import SecondSectionLecture from "./SecondSectionLecture";
import ThirdSectionLecture from "./ThirdSectionLecture";
import ForthSectionLecture from "./ForthSectionLecture";
import FifthSectionLecture from "./FifthSectionLection";

class LecturePage extends Component {
  constructor() {
    super();
    this.state = {
      showReplyFirst: false,
      showReplySecond: false,
      showReplyThird: false,
      showReplyForth: false,
      showReplyFifth: false
    };
  }

  onClickFirstSectionLecture(e) {
    e.preventDefault();
    this.setState({ showReplyFirst: !this.state.showReplyFirst });
  }

  onSecondSectionLecture(e) {
    e.preventDefault();
    this.setState({ showReplySecond: !this.state.showReplySecond });
  }

  onThirdSectionLecture(e) {
    e.preventDefault();
    this.setState({ showReplyThird: !this.state.showReplyThird });
  }

  onClickForthSectionLecture(e) {
    e.preventDefault();
    this.setState({ showReplyForth: !this.state.showReplyForth });
  }

  onClickFifthSectionLecture(e) {
    e.preventDefault();
    this.setState({ showReplyFifth: !this.state.showReplyFifth });
  }

  render() {
    return (
      <div>
        <div onClickFirstSectionLecture={this.onClickFirstSectionLecture.bind(this)}>
          <h1>First section lecture</h1>
        </div>
        {this.state.showReplyFirst && <FifthSectionLecture />}

        <div onSecondSectionLecture={this.onSecondSectionLecture.bind(this)}>
          <h1>Second section lecture</h1>
        </div>
        {this.state.showReplySecond && <SecondSectionLecture />}

        <div onThirdSectionLecture={this.onThirdSectionLecture.bind(this)}>
          <h1>Third section lecture</h1>
        </div>
        {this.state.showReplyThird && <ThirdSectionLecture />}

        <div onClickForthSectionLecture={this.onClickForthSectionLecture.bind(this)}>
          <h1>Forth section lecture</h1>
        </div>
        {this.state.showReplyForth && <ForthSectionLecture />}

        <div onClickFifthSectionLecture={this.onClickFifthSectionLecture.bind(this)}>
          <h1>Fifth section lecture</h1>
        </div>
        {this.state.showReplyFifth && <FifthSectionLecture />}
      </div>
    );
  }
}

export default LecturePage;
