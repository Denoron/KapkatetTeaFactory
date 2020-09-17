package com.propscout.teafactory.controllers.web.admin;

import com.propscout.teafactory.services.TeaRecordsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/records")
public class AdminTeaRecordsController {

    @Value("${app.title}")
    private String app;

    private final TeaRecordsService teaRecordsService;

    public AdminTeaRecordsController(TeaRecordsService teaRecordsService) {
        this.teaRecordsService = teaRecordsService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute("app", app);

        model.addAttribute("title", "Cumulative Tea Weight Records");

        model.addAttribute("cumulativeRecords", teaRecordsService.getThisMonthCumulativeTeaWeight());

        return "admin/records/index";

    }

    @PostMapping("pay")
    public String store() {
        //Initiate farmers payment
        teaRecordsService.payFarmers();

        //Return to the previous page
        return "redirect:/admin/records";
    }
}
