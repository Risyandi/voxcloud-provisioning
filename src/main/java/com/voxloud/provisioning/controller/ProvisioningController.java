package com.voxloud.provisioning.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
public class ProvisioningController {

    @GetMapping("/hello")
    public String hello() {
        return "Hi, Greetings from Spring Boot.!";
    }

}