package com.example.gallerist.service.impl;

import com.example.gallerist.dto.DtoAccount;
import com.example.gallerist.dto.DtoAccountIU;
import com.example.gallerist.model.Account;
import com.example.gallerist.repository.AccountRepository;
import com.example.gallerist.service.IAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private Account createAccount(DtoAccountIU dtoAccountIU) {
        Account account = new Account();
        account.setCreateTime(new Date());
        BeanUtils.copyProperties(dtoAccountIU, account);
        return account;
    }

    @Override
    public DtoAccount saveAccount(DtoAccountIU dtoAccountIU) {
        Account savedAccount = accountRepository.save(createAccount(dtoAccountIU));
        DtoAccount dtoAccount = new DtoAccount();
        BeanUtils.copyProperties(savedAccount, dtoAccount);
        return dtoAccount;
    }
}
