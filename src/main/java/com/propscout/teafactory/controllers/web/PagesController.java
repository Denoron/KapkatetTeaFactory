package com.propscout.teafactory.controllers.web;

import com.propscout.teafactory.models.entities.User;
import com.propscout.teafactory.services.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class PagesController {

    @Value("${app.title}")
    private String app;

    private final UsersService usersService;

    public PagesController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {

        if (principal != null) {
            String email = principal.getName();

            Optional<User> optionalUser = usersService.getUserByEmail(email);

            if (optionalUser.isPresent()) {
                model.addAttribute("user", optionalUser.get());
            }
        }

        model.addAttribute("app", app);
        model.addAttribute("title", "Welcome");

        return "index";
    }
}
