package com.ebanx.takehome.web.controller;

import com.ebanx.takehome.model.Event;
import com.ebanx.takehome.service.AccountService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {

    private final AccountService accountService;

    public EventController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("")
    public ResponseEntity<Object> createEvent(@RequestBody Event event){
        Object result = accountService.saveEvent(event);

        if (result instanceof String){
            return ResponseEntity.status(404).body(0);
        }

        return ResponseEntity.status(201).body(result);
    }
}
