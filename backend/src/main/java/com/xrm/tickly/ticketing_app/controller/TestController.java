package com.xrm.tickly.ticketing_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*")
public class TestController {
    
    @GetMapping("/test")
    public String test() {
        return "API is working!";
    }
}