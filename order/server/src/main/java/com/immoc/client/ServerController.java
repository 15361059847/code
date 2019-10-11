package com.immoc.client;


import org.springframework.web.bind.annotation.*;



@RestController
public class ServerController {

    @GetMapping("/msg")
    public String msg(){
        return "product msg";
    }


}
