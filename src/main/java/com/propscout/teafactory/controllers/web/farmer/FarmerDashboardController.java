package com.propscout.teafactory.controllers.web.farmer;

import com.propscout.teafactory.models.entities.User;
import com.propscout.teafactory.services.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("farmer/dashboard")
public class FarmerDashboardController {

    @Value("${app.title}")
    private String app;

    private UsersService usersService;

    public FarmerDashboardController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public String dashboard(Model model, Principal principal) {

        if (principal == null) return "redirect:/login";

        //Current logged in users email
        String email = principal.getName();

        Optional<User> optionalLoggedInUser = usersService.getUserByEmail(email);

        optionalLoggedInUser.orElseThrow(() -> new UsernameNotFoundException("Currently logged in users username Not found"));

        model.addAttribute("app", app);
        model.addAttribute("title", "Accounts");
        model.addAttribute("user", optionalLoggedInUser.get());

        return "farmer/dashboard";
    }

}
