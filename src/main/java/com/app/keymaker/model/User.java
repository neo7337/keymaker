package com.app.keymaker.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    public String id;

    @Column(name = "email")
    private String email;

    @Column(name = "otp")
    private String otp;

    public User() {
    }

    public User(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    public String getOTP() {
        return otp;
    }

}