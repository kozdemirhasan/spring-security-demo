package de.kozdemir.springsecuritydemo.controller;

import de.kozdemir.springsecuritydemo.model.User;
import de.kozdemir.springsecuritydemo.model.UserDto;
import de.kozdemir.springsecuritydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public MainController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @GetMapping("")
    public String index(Model model){
        return "home";
    }

    @GetMapping({"login","login/{sub}"})
    public String login(@PathVariable Optional<String> sub, Model model) {
        sub.ifPresent(s ->{
            model.addAttribute(s, true);
        });
        return "login";
    }

    /*
    @GetMapping("login/error")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        return "login";
    }
    */


    @GetMapping("register")
    public String register(UserDto userDto, Model model) {
        return "register";
    }

    @PostMapping("register")
    public String registerProcess(@Valid UserDto userDto, BindingResult result, Model model) {

        if(!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "error.userDto", "Passwörter müssen übereinstimmen.");
            // Ein Fehler für das Objekt im userDto und das Feld passwordConfirmation
        }

        if(result.hasErrors()){
            return "register";
        }

        User user = userDto.convert(passwordEncoder);
        userRepository.save(user);
        return "redirect:/register/success";
    }

    @GetMapping("register/success")
    public String registerSuccess(UserDto userDto, Model model){
        model.addAttribute("success", true);
        return "register";
    }




}
