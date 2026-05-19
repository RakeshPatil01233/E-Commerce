package com.rk.ecommerce.controller;


import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/test")
public class Test {


    @GetMapping
    public String test() {
        return "Working.";
    }

}
