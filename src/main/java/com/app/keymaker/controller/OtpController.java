package com.app.keymaker.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.keymaker.model.User;
import com.app.keymaker.model.UserRepository;
import com.app.keymaker.service.*;
import com.app.keymaker.utility.*;

@Controller
public class OtpController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OtpService otpService;

    @Autowired
    public EmailService myEmailService;

    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/generateOTP", method = RequestMethod.GET)
    public @ResponseBody String generateOtp(@RequestParam("email") String email) {
        try {
            String username = email;
            int otp = otpService.generateOTP(username);
            logger.info("OTP : " + otp);
            myEmailService.sendOtpMessage(username, "OTP - KeyMaker",
                    "UserName : " + username + " OTP : " + String.valueOf(otp));
            repository.deleteAll();
            repository.save(new User(username, String.valueOf(otp)));

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failure in sending OTP. Pelase contact the adminsitrator";
        }
    }

    @RequestMapping(value = "/validateOtp", method = RequestMethod.GET)
    public @ResponseBody String validateOtp(@RequestParam("otpnum") int otpnum) {
        final String SUCCESS = "Success";
        final String FAIL = "Unsuccessful";
        logger.info(" Otp Number : " + otpnum);

        for (User user : repository.findAll()) {
            System.out.println(user.getOTP());
            if (String.valueOf(otpnum).equalsIgnoreCase(user.getOTP())) {
                return SUCCESS;
            }
        }
        return FAIL;
    }

    @RequestMapping(value = "/authkeyGenerate", method = RequestMethod.GET)
    public String generateAuthKey() {
        return "generateKey";
    }
}