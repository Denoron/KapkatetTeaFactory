package com.propscout.teafactory.controllers.web.clerk;

import com.propscout.teafactory.models.entities.Center;
import com.propscout.teafactory.models.entities.TeaRecord;
import com.propscout.teafactory.services.CentersService;
import com.propscout.teafactory.services.TeaRecordsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("clerk/records")
public class TakeTeaWeightRecordsController {

    @Value("${app.title}")
    private String app;

    private final CentersService centersService;
    private final TeaRecordsService teaRecordsService;

    public TakeTeaWeightRecordsController(
            CentersService centersService,
            TeaRecordsService teaRecordsService
    ) {
        this.centersService = centersService;
        this.teaRecordsService = teaRecordsService;
    }

    @GetMapping("{centerId}")
    public String index(
            Model model,
            @PathVariable("centerId") Integer centerId
    ) {

        Optional<Center> optionalCenter = centersService.getCenterById(centerId);

        if (optionalCenter.isEmpty()) return "redirect:/clerk/records";

        model.addAttribute("app", app);

        model.addAttribute("title", "Today Records");

        model.addAttribute("centerId", centerId);

        model.addAttribute("teaRecords",
                optionalCenter.get().getTeaRecordList()
                        .stream()
                        .filter(teaRecord -> teaRecord.getCreatedAt().getDayOfYear() == LocalDate.now().getDayOfYear())
                        .collect(Collectors.toList())
        );

        return "clerk/records/take/index";
    }

    @GetMapping("{centerId}/create")
    public String create(
            @PathVariable("centerId") Integer centerId,
            Model model
    ) {

        Optional<Center> optionalCenter = centersService.getCenterById(centerId);

        if (optionalCenter.isEmpty()) return "redirect:/clerk/records";

        model.addAttribute("app", app);

        model.addAttribute("title", "Create Tea Record");

        model.addAttribute("centerId", centerId);

        model.addAttribute("teaRecord", new TeaRecord());

        model.addAttribute("accounts", optionalCenter.get().getAccounts());

        return "clerk/records/take/create";
    }

    @PostMapping("{centerId}")
    public String store(
            @PathVariable("centerId") Integer centerId,
            TeaRecord teaRecord
    ) {

        Optional<Center> optionalCenter = centersService.getCenterById(centerId);

        if (optionalCenter.isEmpty()) return "redirect:/clerk/records/" + centerId + "/create";

        teaRecord.setCenter(optionalCenter.get());

        if (!teaRecordsService.addTeaRecord(teaRecord)) {
            return "redirect:/clerk/records/" + centerId + "/create";
        }

        return "redirect:/clerk/records/" + centerId;
    }
}
