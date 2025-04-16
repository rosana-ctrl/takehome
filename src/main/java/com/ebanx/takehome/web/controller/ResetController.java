package com.ebanx.takehome.web.controller;

import com.ebanx.takehome.service.AccountService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetController {

    private final AccountService accountService;

    public ResetController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        accountService.reset();
        return ResponseEntity.ok("OK");
    }
}
