import React from "react";

export default class Keygen extends React.Component {
    render() {
        return (
            <div>
                <div class='row' id="generateOTP">
                    <div class='col-sm-12'>
                        <form id="generateOtp" name="generateOTP" method="post" style={{ padding: '12px' }}>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input type="text" class="form-control" id="email" placeholder="Enter Email" maxlength="50"></input>
                                </div>
                            </div>
                            <br />
                            <div class="row">
                                <button type="submit" class="btn btn-primary" id="fetch">Generate OTP</button>
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
                                    <input type="submit" class="btn btn-primary btn-block" value="Submit" />
                                </div>
                                <div class="col-md-9">
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <div class="row" id="generateAuthkey">
                    <form id="authKey" name="authKey" method="post" style={{ padding: '12px' }}>
                        <input type="submit" class="btn btn-primary btn-block" value="Generate Auth Key"></input>
                    </form>
                </div>
            </div>
        );
    }
}