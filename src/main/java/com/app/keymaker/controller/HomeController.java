package com.app.keymaker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/keygen")
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