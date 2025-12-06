package com.bootai.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String getHomePage()
    {
        return "homePage";
    }

    @GetMapping("/createProfile")
    public String createProfile()
    {
        return "createProfile";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/product")
    public String productsPage()
    {
        return "productsPage";
    }
}
