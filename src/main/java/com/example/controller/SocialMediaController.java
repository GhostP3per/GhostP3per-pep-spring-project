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
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register") 
    public ResponseEntity<Account> postAccount(@RequestBody Account account) {
        Account newAccount = accountService.registerUser(account);
        return ResponseEntity.status(HttpStatus.OK).body(newAccount);
    }
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        Account foundAccount = accountService.loginUser(account);
        return ResponseEntity.status(HttpStatus.OK).body(foundAccount);    
    }
    @PostMapping("/messages") 
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(createdMessage);
    }
    
    @GetMapping("/messages") 
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(allMessages);
    }
    
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message retrievedMessage = messageService.getMessageById(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(retrievedMessage);
    }
    
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId){
        Integer rowAffected = messageService.deleteMessageById(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(rowAffected);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer messageId, @RequestBody Message message) {
        Integer rowAffected = messageService.updateMessageById(messageId, message);
        return ResponseEntity.status(HttpStatus.OK).body(rowAffected);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer accountId) {
        List<Message> retrievedMessages= messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(retrievedMessages);
    }
}
