package com.propscout.teafactory.controllers.web.admin;

import com.propscout.teafactory.models.dtos.ScheduleItemDto;
import com.propscout.teafactory.models.entities.Role;
import com.propscout.teafactory.models.entities.ScheduleItem;
import com.propscout.teafactory.models.entities.User;
import com.propscout.teafactory.services.CentersService;
import com.propscout.teafactory.services.RolesService;
import com.propscout.teafactory.services.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/schedule")
public class AdminScheduleController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${app.title}")
    private String app;

    private final ScheduleService scheduleService;
    private final CentersService centersService;
    private final RolesService rolesService;

    public AdminScheduleController(
            ScheduleService scheduleService,
            CentersService centersService,
            RolesService rolesService
    ) {
        this.scheduleService = scheduleService;
        this.centersService = centersService;
        this.rolesService = rolesService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute("app", app);
        model.addAttribute("title", "Schedule");
        model.addAttribute("scheduleItems", scheduleService.getScheduleItems());

        return "admin/schedule/index";
    }

    @PostMapping
    public String store(@ModelAttribute ScheduleItemDto scheduleItem) {
        System.out.println(scheduleItem);

        if (scheduleService.addScheduleItem(scheduleItem)) {
            return "redirect:/admin/schedule";
        }

        return "redirect:/admin/schedule/create";
    }

    @GetMapping("create")
    public String create(Model model) {

        model.addAttribute("app", app);

        model.addAttribute("title", "New Schedule Item");

        model.addAttribute("scheduleItem", new ScheduleItem());

        model.addAttribute("users", getClerks());

        model.addAttribute("centers", centersService.getAllCenters());

        return "admin/schedule/create";
    }

    @GetMapping("{scheduleItemId}")
    public String show(
            @PathVariable("scheduleItemId") Long scheduleItemId,
            Model model
    ) {
        final Optional<ScheduleItem> optionalScheduleItem = scheduleService.getScheduleItemById(scheduleItemId);

        if (optionalScheduleItem.isEmpty()) {
            return "redirect:/admin/schedule";
        }

        model.addAttribute("app", app);
        model.addAttribute("title", "Schedule Item");
        model.addAttribute("scheduleItem", optionalScheduleItem.get());

        return "admin/schedule/show";
    }

    @GetMapping("{scheduleItemId}/edit")
    public String edit(
            @PathVariable("scheduleItemId") Long scheduleItemId,
            Model model
    ) {
        final Optional<ScheduleItem> optionalScheduleItem = scheduleService.getScheduleItemById(scheduleItemId);

        if (optionalScheduleItem.isEmpty()) {
            return "redirect:/admin/schedule";
        }

        model.addAttribute("app", app);

        model.addAttribute("title", "Edit schedule item");

        model.addAttribute("scheduleItem", optionalScheduleItem.get());

        model.addAttribute("users", getClerks());

        model.addAttribute("centers", centersService.getAllCenters());

        return "admin/schedule/edit";
    }

    @PostMapping("{scheduleItemId}")
    public String update(
            @PathVariable("scheduleItemId") Long scheduleItemId,
            @ModelAttribute ScheduleItemDto scheduleItemDto
    ) {

        scheduleItemDto.setId(scheduleItemId);

        if (!scheduleService.updateScheduleItemById(scheduleItemDto)) {
            return "redirect:/admin/schedule/" + scheduleItemId + "/edit";
        }

        return "redirect:/admin/schedule/" + scheduleItemId;
    }

    @PostMapping("{scheduleItemId}/delete")
    public String delete(@PathVariable("scheduleItemId") Long scheduleItemId) {

        scheduleService.deleteScheduleItemById(scheduleItemId);

        return "redirect:/admin/schedule";
    }

    private List<User> getClerks() {

        //Get the clerk role and pass the users from the role
        Optional<Role> optionalRole = rolesService.getRoleByTitle("Clerk");

        if (optionalRole.isPresent()) {
            return optionalRole.get().getUsers();
        }

        return Collections.emptyList();
    }
}
