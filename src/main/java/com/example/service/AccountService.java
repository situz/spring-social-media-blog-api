package com.example.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    public Account register(Account account){
        Optional<Account> accountOptional = accountRepository.findByUsername(account.getUsername());
        if (accountOptional.isPresent()){
            return null;
        }
        else{
            Account newAccount = accountOptional.get();
            if ((newAccount.getUsername() != null) && (newAccount.getPassword().length() >= 4)){
                return newAccount;
            }
        }
        return null;
    }
}
