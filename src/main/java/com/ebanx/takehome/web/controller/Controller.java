package com.ebanx.takehome.web.controller;

import com.ebanx.takehome.model.Event;
import com.ebanx.takehome.service.AccountService;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private AccountService accountService;

    public Controller(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/xxx")
    public ResponseEntity<Object> getBalance(@RequestParam("account_id") String accountId){
        Integer balance = accountService.getBalance(accountId);
        if (balance == null){
            return ResponseEntity.status(404).body(0);
        }
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/yyy")
    public ResponseEntity<Object> createEvent(@RequestBody Event event){
        Object result = accountService.saveEvent(event);
        if (result == null){
            return  ResponseEntity.status(404).body(0);
        }
        return ResponseEntity.status(201).body(result);
    }
}
