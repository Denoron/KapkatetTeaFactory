package com.propscout.teafactory.controllers.web.clerk;

import com.propscout.teafactory.models.entities.ScheduleItem;
import com.propscout.teafactory.models.entities.User;
import com.propscout.teafactory.services.ScheduleService;
import com.propscout.teafactory.services.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("clerk/schedule")
public class ClerkScheduleController {

    @Value("${app.title}")
    private String app;

    private final ScheduleService scheduleService;
    private final UsersService usersService;

    public ClerkScheduleController(
            ScheduleService scheduleService,
            UsersService usersService
    ) {
        this.scheduleService = scheduleService;
        this.usersService = usersService;
    }

    @GetMapping
    public String index(Model model, Principal principal) throws Exception {

        if (principal == null) {
            return "redirect:/login";
        }

        String email = principal.getName();

        Optional<User> optionalUser = usersService.getUserByEmail(email);

        optionalUser.orElseThrow(() -> new Exception("The logged in user account can not be found in the database for some reason"));

        model.addAttribute("app", app);

        model.addAttribute("title", "Clerk Schedule Items");

        model.addAttribute("scheduleItems", optionalUser.get().getScheduleItems());

        return "clerk/schedule/index";

    }

    @GetMapping("{scheduleItemId}")
    private String show(
            @PathVariable("scheduleItemId") Long scheduleItemId,
            Model model
    ) {
        final Optional<ScheduleItem> optionalScheduleItem = scheduleService.getScheduleItemById(scheduleItemId);

        if (optionalScheduleItem.isEmpty()) {
            return "redirect:/clerk/schedule";
        }

        model.addAttribute("app", app);
        model.addAttribute("title", "Schedule Item");

        model.addAttribute("scheduleItem", optionalScheduleItem.get());


        return "clerk/schedule/show";
    }
}
