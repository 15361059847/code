package com.example.demo.test.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class Testcontroller {


    @GetMapping("/getTest")
    public String getUser(){
        return "Test2";
    }
}
