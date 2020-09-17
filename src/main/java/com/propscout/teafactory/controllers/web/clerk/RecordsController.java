package com.propscout.teafactory.controllers.web.clerk;

import com.propscout.teafactory.models.entities.ScheduleItem;
import com.propscout.teafactory.models.entities.User;
import com.propscout.teafactory.services.ScheduleService;
import com.propscout.teafactory.services.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("clerk/records")
public class RecordsController {

    @Value("${app.title}")
    private String app;

    private final ScheduleService scheduleService;
    private final UsersService usersService;

    public RecordsController(
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

        model.addAttribute("title", "Clerk Months Schedule Items");

        final List<ScheduleItem> thisMonthScheduleItems = optionalUser.get().getScheduleItems().stream().filter(scheduleItem -> {
            LocalDate localDate = LocalDate.now();

            return localDate.getMonthValue() == scheduleItem.getMonth();
        }).collect(Collectors.toList());

        model.addAttribute("scheduleItems", thisMonthScheduleItems);

        return "clerk/records/index";
    }

}
