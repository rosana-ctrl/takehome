package com.ebanx.takehome.web.controller;

import com.ebanx.takehome.service.AccountService;
import com.ebanx.takehome.validator.EventValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final AccountService accountService;

    public BalanceController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getBalance(@RequestParam("account_id") String accountId) {
        try {
            EventValidator.validateAccountId(accountId);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("invalid account_id");
        }

        Integer balance = accountService.getBalance(accountId);

        if (balance == null) {
            return ResponseEntity.status(404).body(0);
        }

        return ResponseEntity.ok(balance);
    }
}
