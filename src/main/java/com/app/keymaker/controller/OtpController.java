package com.app.keymaker.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.keymaker.service.*;
import com.app.keymaker.utility.*;

@Controller
public class OtpController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OtpService otpService;

    @Autowired
    public EmailService myEmailService;

    @Generated("generateOTP")
    public String generateOtp() {
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        int otp = otpService.generateOTP(username);
        logger.info("OTP : " + otp);
        EmailTemplate template = new EmailTemplate("SendOtp.html");
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("user", username);
        replacements.put("otpnum", String.valueOf(otp));
        String message = template.getTemplate(replacements);
        myEmailService.sendOtpMessage("@gmail.com", "OTP -SpringBoot", message);
        return "otppage";
    }

    @RequestMapping(value = "/validateOtp", method = RequestMethod.GET)
    public @ResponseBody String validateOtp(@RequestParam("otpnum") int otpnum) {

        final String SUCCESS = "Entered Otp is valid";

        final String FAIL = "Entered Otp is NOT valid. Please Retry!";

        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "";

        logger.info(" Otp Number : " + otpnum);

        // Validate the Otp
        if (otpnum >= 0) {
            int serverOtp = otpService.getOtp(username);

            if (serverOtp > 0) {
                if (otpnum == serverOtp) {
                    otpService.clearOTP(username);
                    return ("Entered Otp is valid");
                } else {
                    return SUCCESS;
                }
            } else {
                return FAIL;
            }
        } else {
            return FAIL;
        }
    }

}