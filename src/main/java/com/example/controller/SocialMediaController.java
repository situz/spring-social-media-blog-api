package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController (AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
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

    @PostMapping("messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message){
        Message newMessage = messageService.postMessage(message);
        if (newMessage != null){
            return ResponseEntity.ok(newMessage);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.ok(allMessages);
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        Message getMessage = messageService.getMessageById(messageId);
        if (getMessage != null){
            return ResponseEntity.ok(getMessage);
        }
        else{
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        int rowsUpdated = messageService.deleteMessageById(messageId);
        if (rowsUpdated == 1){
            return ResponseEntity.ok(1);
        }
        else{
            return ResponseEntity.ok().build();
        }
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> patchMessageByIdAndMessageText(@PathVariable int messageId, @RequestBody Message message){
        if (messageService.patchMessageByIdAndMessageText(messageId, message.getMessageText()) == 1){
            return ResponseEntity.ok(1);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int accountId){
        List<Message> messagesByAccountId = messageService.getAllMessagesByPostedBy(accountId);
        return ResponseEntity.ok(messagesByAccountId);
    }


}
