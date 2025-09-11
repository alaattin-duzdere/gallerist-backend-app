package com.example.gallerist.controller.impl;

import com.example.gallerist.controller.IRestAccountController;
import com.example.gallerist.controller.RestBaseController;
import com.example.gallerist.controller.RootEntity;
import com.example.gallerist.dto.DtoAccount;
import com.example.gallerist.dto.DtoAccountIU;
import com.example.gallerist.service.IAccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/account")
public class RestAccountControllerImpl extends RestBaseController implements IRestAccountController {

    private final IAccountService accountService;

    public RestAccountControllerImpl(IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/save")
    @Override
    public RootEntity<DtoAccount> saveAccount(@Valid @RequestBody DtoAccountIU dtoAccountIU) {
        return ok(accountService.saveAccount(dtoAccountIU));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoAccount> getAccountById(@PathVariable Long id) {
        return ok(accountService.getAdressById(id));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoAccount> updateAccount(@PathVariable Long id,@RequestBody DtoAccountIU dtoAccountIU) {
        return ok(accountService.updateAccount(id, dtoAccountIU));
    }
}
