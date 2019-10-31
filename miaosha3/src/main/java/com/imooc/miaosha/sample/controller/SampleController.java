package com.imooc.miaosha.sample.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/sample")
public class SampleController {


    @RequestMapping("/thymeleaf")
    public ModelAndView thymeleaf() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/hello.html");
        mv.addObject("name","1321");
        return mv;
    }

}
