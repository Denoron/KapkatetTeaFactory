package com.propscout.teafactory.controllers.web.auth;

import com.propscout.teafactory.models.entities.User;
import com.propscout.teafactory.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("register")
public class RegisterController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${app.title}")
    private String app;

    private UsersService usersService;

    public RegisterController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public String showForm(Model model, Principal principal) {

        //If the user is already authenticated redirect to the home page
        if (principal != null) return "redirect:/";

        model.addAttribute("app", app);
        model.addAttribute("title", "Register");
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") User user, BindingResult bindingResult) {

        Optional<User> userOption = usersService.getUserByEmail(user.getEmail());

        userOption.ifPresent(userFound -> {
            //Set an error if the email already exists
            bindingResult.rejectValue("email", "email", "There is already a user registered with the email provided");
        });

        if (!bindingResult.hasErrors()) {
            final User newUser = usersService.saveUser(user);

            if (newUser != null) {
                logger.info("User successfully registered");
                return "redirect:/login";
            }
        }

        return "redirect:register";
    }

}
