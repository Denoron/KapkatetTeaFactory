package com.propscout.teafactory.controllers.web.farmer;

import com.propscout.teafactory.models.entities.Account;
import com.propscout.teafactory.models.entities.User;
import com.propscout.teafactory.services.AccountsService;
import com.propscout.teafactory.services.CentersService;
import com.propscout.teafactory.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("farmer/accounts")
public class AccountsController {

    //Logger
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${app.title}")
    private String app;

    private final AccountsService accountsService;
    private final UsersService usersService;
    private final CentersService centersService;

    public AccountsController(
            AccountsService accountsService,
            UsersService usersService,
            CentersService centersService
    ) {
        this.accountsService = accountsService;
        this.usersService = usersService;
        this.centersService = centersService;
    }

    @GetMapping
    public String index(Principal principal, Model model) throws Exception {

        if (principal == null) return "redirect:/login";

        //Current logged in users email
        String email = principal.getName();

        Optional<User> optionalLoggedInUser = usersService.getUserByEmail(email);

        optionalLoggedInUser.orElseThrow(() -> new UsernameNotFoundException("Currently logged in users username Not found"));

        model.addAttribute("app", app);
        model.addAttribute("title", "Accounts");
        model.addAttribute("user", optionalLoggedInUser.get());

        return "farmer/accounts/index";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("app", app);
        model.addAttribute("title", "Create Account");
        model.addAttribute("account", new Account());
        model.addAttribute("centers", centersService.getAllCenters());

        return "farmer/accounts/create";
    }

    @PostMapping
    public String store(@ModelAttribute("account") Account account, Principal principal) {

        //Logg email for the current user
        logger.info(principal.getName());

        //Current logged in users email
        String email = principal.getName();
        Optional<User> optionalLoggedInUser = usersService.getUserByEmail(email);

        //Set the user for the account
        account.setUser(optionalLoggedInUser.get());

        if (accountsService.addAccount(account)) {
            return "redirect:/farmer/accounts";
        }

        return "redirect:/farmer/accounts/create";
    }

    @GetMapping("{accountId}")
    public String show(
            @PathVariable("accountId") Integer accountId,
            Model model
    ) {
        //Try getting the account based on the id
        Optional<Account> optionalAccount = accountsService.getAccountById(accountId);

        //Check if the account really exists
        if (optionalAccount.isEmpty()) {
            return "redirect:/farmer/accounts";
        }

        //Set the view attributes to the model
        model.addAttribute("app", app);
        model.addAttribute("title", "User Account");
        model.addAttribute("account", optionalAccount.get());

        return "farmer/accounts/show";
    }

    @GetMapping("{accountId}/edit")
    public String edit(
            @PathVariable("accountId") Integer accountId,
            Model model
    ) {
        //Try getting the account based on the id
        Optional<Account> optionalAccount = accountsService.getAccountById(accountId);

        //Check if the account really exists
        if (optionalAccount.isEmpty()) {
            return "redirect:/farmer/accounts";
        }

        //Set the view attributes to the model
        model.addAttribute("app", app);
        model.addAttribute("title", "User Account");
        model.addAttribute("account", optionalAccount.get());
        model.addAttribute("centers", centersService.getAllCenters());

        return "farmer/accounts/edit";
    }

    @PostMapping("{accountId}")
    public String update(
            @PathVariable("accountId") Integer accountId,
            Account account
    ) {
        //Set the account id
        account.setId(accountId);

        //Try updating the account if it exists
        if (accountsService.updateAccount(account)) {
            return "redirect:/farmer/accounts/" + accountId;
        }

        return "redirect:/farmer/account/" + accountId + "/edit";
    }

    @PostMapping("{accountId}/delete")
    public String destroy(@PathVariable("accountId") Integer accountId) {

        accountsService.deleteAccount(accountId);

        return "redirect:/farmer/accounts/";
    }

}
