package com.propscout.teafactory.controllers.web.farmer;

import com.propscout.teafactory.models.entities.Account;
import com.propscout.teafactory.services.AccountsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("farmer/accounts")
public class FarmerRecordsController {

    @Value("${app.title}")
    private String app;

    private final AccountsService accountsService;

    public FarmerRecordsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("{accountId}/records")
    public String index(
            @PathVariable("accountId") Integer accountId,
            Model model
    ) throws Exception {

        Optional<Account> optionalAccount = accountsService.getAccountById(accountId);

        optionalAccount.orElseThrow(() -> new Exception("No Account Info"));

        model.addAttribute("app", app);

        model.addAttribute("title", "Account Records");

        model.addAttribute("teaRecords", optionalAccount.get().getTeaRecordItems());

        return "farmer/accounts/records/index";
    }
}
