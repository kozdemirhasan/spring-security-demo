package de.kozdemir.springsecuritydemo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

//    @Secured("ROLE_ADMIN") // Wenn JSR-250 verwendet wird
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public String admin(Model model){
        return "admin";
    }

}
