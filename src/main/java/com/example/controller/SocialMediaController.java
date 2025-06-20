package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    private AccountService accountService;
    public SocialMediaController (AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        if  (!((account.getUsername() != null) && (account.getUsername().length() > 0) && (account.getPassword() != null) && (account.getPassword().length() >= 4))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Account newAccount = accountService.register(account);
        if (newAccount != null){
            return ResponseEntity.ok(newAccount);
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account loginAccount = accountService.login(account);
        if (loginAccount != null){
            return ResponseEntity.ok(loginAccount);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
