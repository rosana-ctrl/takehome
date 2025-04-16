package com.ebanx.takehome.service;

import com.ebanx.takehome.model.Account;
import com.ebanx.takehome.model.Event;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    private final Map<String, Account> accounts = new HashMap<>();

    public Integer getBalance(String accountId) {
        Account account = accounts.get(accountId);
        if (account != null){
            return account.getBalance();
        }
        return null;
    }

    public Object saveEvent(Event event) {
        return switch (event.getType()) {
            case DEPOSIT -> handleDeposit(event);
            case WITHDRAW -> handleWithdraw(event);
            case TRANSFER -> handleTransfer(event);
            default -> "Unknown event type";
        };
    }

    private Map<String, Object> handleDeposit(Event event){
        Account destination = accounts.computeIfAbsent(event.getDestination(), Account::new);
        destination.deposit(event.getAmount());
        return Map.of(
                "destination", Map.of(
                        "id", destination.getId(),
                        "balance", destination.getBalance()
                )
        );
    }

    private Object handleWithdraw(Event event){
        return false;
    }

    private Object handleTransfer(Event event){
        return false;
    }

}
