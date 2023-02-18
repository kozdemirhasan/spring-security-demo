package de.kozdemir.springsecuritydemo.controller;

import de.kozdemir.springsecuritydemo.model.Token;
import de.kozdemir.springsecuritydemo.model.User;
import de.kozdemir.springsecuritydemo.model.UserDto;
import de.kozdemir.springsecuritydemo.model.UserStatus;
import de.kozdemir.springsecuritydemo.repository.TokenRepository;
import de.kozdemir.springsecuritydemo.repository.UserRepository;
import de.kozdemir.springsecuritydemo.service.CustomEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomEmailService emailService;

    @Autowired
    private TokenRepository tokenRepository;


    @GetMapping("")
    public String index(Model model) {
        return "home";
    }

    @GetMapping({"login", "login/{sub}"})
    public String login(@PathVariable Optional<String> sub, Model model) {
        sub.ifPresent(s -> {
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

    @GetMapping("mail")
    public String sendMail(Model model) {
        emailService.sendSimpleEmail("p.parker@shield.org", "Du bist raus...", "Das reicht. Du bist bei uns raus....");
        return "redirect:/";
    }


    @GetMapping("register")
    public String register(UserDto userDto, Model model) {
        return "register";
    }

    @GetMapping("forgot")
    public String forgotForm(UserDto userDto, Model model) {
        model.addAttribute("userDto", userDto);
        return "forgot";
    }

    @PostMapping("forgot")
    public String sendForgotEmail(UserDto userDto, Model model) throws MessagingException {

        // Wenn nicht vorhanden, soll eine Exception geworfen werden
        Optional<User> opt = userRepository.findByEmailIgnoreCase(userDto.getEmail());
        if(opt.isPresent()) {
            User user = opt.get(); // get liefert bei einem leeren Optional eine Exception
            Token token = new Token(user, Token.TokenType.PASSWORD);
            tokenRepository.save(token);
            emailService.sendHtmlForgotEmail(user, token.getId());
        }
        model.addAttribute("sent", true);
        return "forgot";
    }

    @PostMapping("register")
    public String registerProcess(@Valid UserDto userDto, BindingResult result, Model model) throws MessagingException {

        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "error.userDto", "Passwörter müssen übereinstimmen.");
            // Ein Fehler für das Objekt im userDto und das Feld passwordConfirmation
        }

        if (result.hasErrors()) {
            return "register";
        }
        User user = userDto.convert(passwordEncoder);
        userRepository.save(user);
        Token token = new Token(user, Token.TokenType.ACTIVATION);
        tokenRepository.save(token);
//        emailService.sendSimpleEmail(user.getEmail(), "Registrierung", "Du hast dich erfolgreich registriert");
//        emailService.sendHtmlEmail(user.getEmail(), "Registrierung");
        emailService.sendHtmlRegisterEmail(user, token.getId());
        return "redirect:/register/success";
    }

    @GetMapping("register/success")
    public String registerSuccess(UserDto userDto, Model model) {
        model.addAttribute("success", true);
        return "register";
    }

    @GetMapping("activate")
    public String checkToken(@RequestParam("token") String tokenStr, Model model) {
        try {
            Optional<Token> opt = tokenRepository.findByIdAndType(UUID.fromString(tokenStr), Token.TokenType.ACTIVATION);

            if (opt.isPresent()) {
                Token token = opt.get();
                User user = token.getUser();
                user.setStatus(UserStatus.ACTIVE);
                userRepository.save(user);
                tokenRepository.delete(token);

                model.addAttribute("success", true);
            } else {
                throw new RuntimeException("Token nicht gefunden.");
            }
        } catch (Exception e) {
            model.addAttribute("error", true);
        }
        return "activate";
    }

    @GetMapping("forgot/reset")
    public String checkForgotToken(@RequestParam("token") String tokenStr, Model model) {
        try {
            Optional<Token> opt = tokenRepository.findByIdAndType(UUID.fromString(tokenStr), Token.TokenType.PASSWORD);

            if (opt.isPresent()) {
                Token token = opt.get();
                User user = token.getUser();
                UserDto userDto = new UserDto(); //TODO: Konvertierung von user nach userDto einbauen
                userDto.setUsername(user.getUsername());
                userDto.setEmail(user.getEmail());
                model.addAttribute("userDto", userDto);

            }
            else {
                throw new RuntimeException("Token nicht gefunden.");
            }
        } catch (Exception e) {
            model.addAttribute("error", true);
        }
        return "reset-password";
    }

    @PostMapping("forgot/reset")
    public String resetPassword(UserDto userDto, Model model) {
        Optional<User> opt = userRepository.findByEmailIgnoreCase(userDto.getEmail());
        if(opt.isPresent()) {
            User user = opt.get();
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            // TODO: alten Token löschen
            model.addAttribute("success", true);
        }
        else {
            model.addAttribute("error", true);
        }
        return "reset-password";
    }

}
