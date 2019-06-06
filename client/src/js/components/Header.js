import React from "react";

export default class Header extends React.Component {
  handleChange(e) {
    const title = e.target.value;
    this.props.changeTitle(title);
  }

  render() {
    return (
      <div>
        <nav class="navbar navbar-dark bg-dark">
          <a class="navbar-brand" href="">
            <i>{this.props.title}</i>
          </a>
        </nav>
      </div>
    );
  }
}
