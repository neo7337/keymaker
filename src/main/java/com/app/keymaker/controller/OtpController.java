package com.app.keymaker.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.keymaker.model.User;
import com.app.keymaker.model.UserRepository;
import com.app.keymaker.service.*;
import com.app.keymaker.utility.*;
import com.mongodb.DBCollection;

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
            // repository.deleteAll();

            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(username));
            Update update = new Update();
            update.set("otp", otp);
            // repository.upsert(query, User.class);
            for (User user : repository.findAll()) {
                // System.out.println(user.getOTP());
                if (String.valueOf(email).equalsIgnoreCase(user.getEmail())) {
                    repository.deleteById(user.getID());
                    repository.save(new User(username, String.valueOf(otp)));
                    break;
                }
            }
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
                return SUCCESS + "," + generateAuthKey(user.getEmail(), user.getOTP());
            }
        }
        return FAIL;
    }

    public String generateAuthKey(String email, String otp) {

        String SUCCESS = "Success";
        String FAIL = "Fail";

        KeyGenerator keyGen = new KeyGenerator();
        String generatedKey = keyGen.randomKeyGenerator();

        return generatedKey;
    }
}