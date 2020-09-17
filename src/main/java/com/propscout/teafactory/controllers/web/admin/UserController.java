package com.propscout.teafactory.controllers.web.admin;

import com.propscout.teafactory.models.entities.User;
import com.propscout.teafactory.services.RolesService;
import com.propscout.teafactory.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private UsersService usersService;
    private RolesService rolesService;

    @Value("${app.title}")
    private String app;

    public UserController(UsersService usersService, RolesService rolesService) {
        this.usersService = usersService;
        this.rolesService = rolesService;
    }

    @GetMapping
    public String index(Model model) {

        List<User> users = usersService.getAllUsers();

        model.addAttribute("app", app);
        model.addAttribute("title", "Admin Users");
        model.addAttribute("users", users);

        return "admin/users/index";
    }

    @GetMapping("{userId}")
    public String show(@PathVariable("userId") Integer userId, Model model) {

        Optional<User> optionalUser = usersService.getUserById(userId);

        if (optionalUser.isEmpty()) {
            return "redirect:/admin/users";
        }

        model.addAttribute("app", app);
        model.addAttribute("title", "Admin User");
        model.addAttribute("user", optionalUser.get());

        return "admin/users/show";

    }

    @GetMapping("{userId}/edit")
    public String edit(@PathVariable("userId") Integer userId, Model model) {

        Optional<User> optionalUser = usersService.getUserById(userId);

        if (optionalUser.isEmpty()) {
            return "redirect:/admin/users";
        }

        model.addAttribute("app", app);
        model.addAttribute("title", "Admin Edit User");
        model.addAttribute("user", optionalUser.get());
        model.addAttribute("roles", rolesService.getAllRoles());

        return "admin/users/edit";

    }

    @PostMapping("{userId}")
    public String update(
            @PathVariable("userId") Integer userId,
            @ModelAttribute("user") User user
    ) {

        //Add id to the shoveled user instance
        user.setId(userId);

        if (usersService.updateUserById(user)) {
            return "redirect:/admin/users/" + userId;
        }

        return "redirect:/admin/users/" + userId + "/edit";

    }
}
