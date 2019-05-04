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
        final String SUCCESS = "Entered Otp is valid";
        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
        String username = "";
        logger.info(" Otp Number : " + otpnum);

        boolean result = repository.equals(String.valueOf(otpnum));

        logger.info(String.valueOf(result));
        // Validate the Otp
        /*
         * if (otpnum >= 0) { int serverOtp = otpService.getOtp(username); if (serverOtp
         * > 0) { if (otpnum == serverOtp) { otpService.clearOTP(username); return
         * ("Entered Otp is valid"); } else { return SUCCESS; } } else { return FAIL; }
         * } else { return FAIL; }
         */
        return SUCCESS;
    }
}