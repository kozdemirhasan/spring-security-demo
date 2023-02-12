package de.kozdemir.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String index(Model model){
        return "home";
    }

    @GetMapping("login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("login/error")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        return "login";
    }

    @GetMapping("register")
    public String register(Model model) {
        return "register";
    }




}
