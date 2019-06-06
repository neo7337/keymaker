import React from "react";


export default class Homepage extends React.Component {

    constructor(props) {
        super(props);
    }

    handlePage(pageCategory) {
        this.props.togglePage(pageCategory);
    }

    render() {
        return (
            <div>
                <div class="container" id="about">
                    <div class='row'>
                        <div class='col-sm-6'>
                            <form style={{ padding: '12px' }}>
                                <div class="card">
                                    <div class="card-header">
                                        Featured
                                    </div>
                                    <div class="card-body">
                                        <h5 class="card-title">Auth key Generation</h5>
                                        <p class="card-text">Proceed further to generate the activation code.</p>
                                        <a onClick={() => this.handlePage('keyGen')} class="btn btn-primary">Proceed</a>
                                    </div>
                                </div>
                                <br />
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class='cl-sm-12'>
                            <div id='res'></div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
