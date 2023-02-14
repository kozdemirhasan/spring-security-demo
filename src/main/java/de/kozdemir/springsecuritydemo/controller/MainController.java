package de.kozdemir.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class MainController {

    @GetMapping("")
    public String index(Model model){
        return "home";
    }

    @GetMapping({"login","login/{sub}"})
    public String login(@PathVariable Optional<String> sub, Model model) {
        sub.ifPresent(s ->{
            model.addAttribute("s", true);
        });
        return "login";
    }

//    @GetMapping("login/error")
//    public String loginError(Model model) {
//        model.addAttribute("error", true);
//        return "login";
//    }

    @GetMapping("register")
    public String register(Model model) {
        return "register";
    }




}
