package com.propscout.teafactory.controllers.web.admin;

import com.propscout.teafactory.models.entities.Center;
import com.propscout.teafactory.services.CentersService;
import com.propscout.teafactory.services.LocationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/centers")
public class CentersController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${app.title}")
    private String app;

    private final CentersService centersService;
    private final LocationsService locationsService;

    public CentersController(CentersService centersService, LocationsService locationsService) {
        this.centersService = centersService;
        this.locationsService = locationsService;
    }

    @GetMapping
    public String index(Model model) {

        List<Center> centers = centersService.getAllCenters();

        model.addAttribute("app", app);
        model.addAttribute("title", "Admin Centers");
        model.addAttribute("centers", centers);

        logger.info(centers.toString());

        return "admin/centers/index";
    }

    @GetMapping("create")
    public String create(Model model) {

        model.addAttribute("app", app);
        model.addAttribute("title", "Admin Create Center");
        model.addAttribute("center", new Center());
        model.addAttribute("locations", locationsService.getAllLocations());

        return "admin/centers/create";
    }

    @PostMapping
    public String store(@ModelAttribute("center") Center center) {

        logger.info(center.toString());

        if (centersService.addCenter(center)) {
            return "redirect:/admin/centers";
        }

        return "redirect:/admin/centers/create";
    }

    @GetMapping("{centerId}")
    public String show(
            @PathVariable("centerId") Integer centerId,
            Model model
    ) {
        final Optional<Center> optionalCenter = centersService.getCenterById(centerId);

        if (optionalCenter.isEmpty()) {
            return "redirect:/admin/centers";
        }

        model.addAttribute("app", app);
        model.addAttribute("title", optionalCenter.get().getName());
        model.addAttribute("center", optionalCenter.get());

        return "admin/centers/show";
    }

    @GetMapping("{centerId}/edit")
    public String edit(
            @PathVariable("centerId") Integer centerId,
            Model model
    ) {
        final Optional<Center> optionalCenter = centersService.getCenterById(centerId);

        if (optionalCenter.isEmpty()) {
            return "redirect:/admin/centers";
        }

        model.addAttribute("app", app);
        model.addAttribute("title", "Edit" + optionalCenter.get().getName());
        model.addAttribute("center", optionalCenter.get());
        model.addAttribute("locations", locationsService.getAllLocations());

        return "admin/centers/edit";
    }

    @PostMapping("{centerId}")
    public String update(
            @PathVariable("centerId") Integer centerId,
            @ModelAttribute("center") Center center
    ) {

        center.setId(centerId);

        if (!centersService.updateCenterById(center)) {
            return "redirect:/admin/centers/" + centerId + "/edit";
        }

        return "redirect:/admin/centers/" + centerId;
    }

    @PostMapping("{centerId}/delete")
    public String delete(@PathVariable("centerId") Integer centerId) {

        centersService.deleteCenterById(centerId);

        return "redirect:/admin/centers";
    }
}
