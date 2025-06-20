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
        if ((account.getUsername() != null) && (account.getUsername().length() > 0) && (account.getPassword() != null) && (account.getPassword().length() >= 4)){
            if(accountRepository.findByUsername(account.getUsername()).isPresent()){
                return null;
            }
            Account newAccount = new Account(account.getUsername(), account.getPassword());
            return accountRepository.save(newAccount);
        }
        else{
            return null;
        }
    }
}
