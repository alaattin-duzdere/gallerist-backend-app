package com.example.gallerist.service.impl;

import com.example.gallerist.dto.DtoAccount;
import com.example.gallerist.dto.DtoAccountIU;
import com.example.gallerist.exceptions.BaseException;
import com.example.gallerist.exceptions.ErrorMessage;
import com.example.gallerist.exceptions.MessageType;
import com.example.gallerist.model.Account;
import com.example.gallerist.repository.AccountRepository;
import com.example.gallerist.service.IAccountService;
import com.example.gallerist.utils.CustomPermissionEvaluator;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;

    private final CustomPermissionEvaluator customPermissionEvaluator;

    public AccountServiceImpl(AccountRepository accountRepository, CustomPermissionEvaluator customPermissionEvaluator) {
        this.accountRepository = accountRepository;
        this.customPermissionEvaluator = customPermissionEvaluator;
        this.customPermissionEvaluator.setRepository(Account.class);
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

    @PreAuthorize("hasPermission(#id, 'Account', 'read')")
    @Override
    public DtoAccount getAdressById(Long id) {
        Optional<Account> optAccount = accountRepository.findById(id);
        if(optAccount.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.No_Record_Exist, "Account with id "+id+" does not exist"));
        }
        DtoAccount dtoAccount = new DtoAccount();

        BeanUtils.copyProperties(optAccount.get(), dtoAccount);
        return dtoAccount;
    }

    @PreAuthorize("hasPermission(#id, 'Account', 'write')")
    @Override
    public DtoAccount updateAccount(Long id, DtoAccountIU dtoAccountIU) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new BaseException(new ErrorMessage(MessageType.No_Record_Exist, "Account with id "+id+" does not exist"))
        );
        BeanUtils.copyProperties(createAccount(dtoAccountIU), account,"id", "createTime");
        Account savedAccount = accountRepository.save(account);

        DtoAccount dtoAccount = new DtoAccount();
        BeanUtils.copyProperties(savedAccount, dtoAccount);
        return dtoAccount;
    }
}
