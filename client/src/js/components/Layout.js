import React from "react";

import Footer from "./Footer";
import Header from "./Header";
import Content from "./Content";

export default class Layout extends React.Component {
  constructor() {
    super();
    this.state = {
      title: "keymaker",
    };
  }

  changeTitle(title) {
    this.setState({ title });
  }

  render() {

    return (
      <div>
        <Header changeTitle={this.changeTitle.bind(this)} title={this.state.title} />
        <Content></Content>
        <Footer></Footer>
      </div>
    );
  }
}
