import React from "react";
import { Button } from 'semantic-ui-react'


export default class Keygen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            fields: {},
            errors: {}
        }
    }

    /* handlePage(pageCategory) {
        this.props.togglePage(pageCategory);
    } */

    handleChange(field, e) {
        let fields = this.state.fields;
        fields[field] = e.target.value;
        this.setState({ fields });
    }

    generateOTP(e) {
        if (this.handleValidation()) {
            alert("form submitted");
        } else {
            alert("form has errors");
            this.handlePage('keyGen')
        }
    }

    handleValidation() {
        let fields = this.state.fields;
        let errors = {};
        let formIsValid = true;

        if (!fields["email"]) {
            formIsValid = false;
            errors["email"] = "Cannot be empty";
        }

        if (typeof fields["email"] !== "undefined") {
            let lastAtPos = fields["email"].lastIndexOf('@');
            let lastDotPos = fields["email"].lastIndexOf('.');

            if (!(lastAtPos < lastDotPos && lastAtPos > 0 && fields["email"].indexOf('@@') == -1 && lastDotPos > 2 && (fields["email"].length - lastDotPos) > 2)) {
                formIsValid = false;
                errors["email"] = "Email is not valid";
            }
        }

        this.setState({ errors: errors });
        return formIsValid;
    }


    render() {
        return (
            <div class="container">
                <div class='row' id="generateOTP">
                    <div class='col-sm-12'>
                        <form id="generateOtp" name="generateOTP" onSubmit={this.generateOTP.bind(this)} style={{ padding: '12px' }}>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input type="text" class="form-control" id="email" onChange={this.handleChange.bind(this, "email")} placeholder="Enter Email" maxlength="50"></input>
                                </div>
                            </div>
                            <br />
                            <div class="row">
                                <Button type="submit" class="btn btn-primary" id="fetch">Generate OTP</Button>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row" id="validateOTP">
                    <form id="validateOtp" name="validateOtp" method="post" style={{ padding: '12px' }}>
                        <fieldset>
                            <div class="row">
                                <div class="form-group">
                                    <div class="form-group col-md-6">
                                        <input type="text" name="otpnum" id="otpnum" class="form-control" required="true"
                                            autofocus="true" placeholder="Enter OTP" />
                                    </div>
                                    <div class="col-md-6">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <Button type="submit" class="btn btn-primary btn-block" value="Submit">Submit</Button>
                                </div>
                                <div class="col-md-9">
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <div class="row" id="generateAuthkey">
                    <form id="authKey" name="authKey" method="post" style={{ padding: '12px' }}>
                        <Button type="submit" class="btn btn-primary btn-block" value="Generate Auth Key">Generate Auth Key</Button>
                    </form>
                </div>
            </div>
        );
    }
}