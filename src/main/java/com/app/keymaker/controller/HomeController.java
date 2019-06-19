package com.app.keymaker.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/index")
    public String homePage() {
        return "Hello, the time at the server is now " + new Date() + "\n";
    }

    @GetMapping("/generateKey")
    public String keyGen() {
        return "keyGen";
    }

    @GetMapping("/gpsCode")
    public String gpsCode() {
        return "gpsCode";
    }

    @GetMapping("/promoCode")
    public String promoCode() {
        return "promoCode";
    }
}