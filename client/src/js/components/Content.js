import React from "react";
import Homepage from "../Pages/Homepage";
import Keygen from "../Pages/Keygen";


export default class Content extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentPage: 'homePage'
        };
    }

    getPage(currPage) {
        const pages = {
            homePage: <Homepage></Homepage>,
            keyGen: <Keygen></Keygen>
        }
        return pages[currPage];
    }

    togglePage(currPage) {
        this.setState({ currPage });
    }

    render() {
        return (
            <div>
                {this.getPage(this.state.currentPage)}
            </div>
        );
    }
}
