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
            case deposit -> handleDeposit(event);
            case withdraw -> handleWithdraw(event);
            case transfer -> handleTransfer(event);
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
        Account origin = accounts.get(event.getOrigin());
        if (origin == null || origin.getBalance() < event.getAmount()) {
            return null;
        }
        origin.withdraw(event.getAmount());
        return Map.of(
                "origin", Map.of(
                        "id", origin.getId(),
                        "balance", origin.getBalance()
                )
        );
    }

    private Object handleTransfer(Event event){
        Account origin = accounts.get(event.getOrigin());
        if (origin == null || origin.getBalance() < event.getAmount()){
            return  null;
        }
        Account destination = accounts.computeIfAbsent(event.getDestination(), Account::new);
        origin.withdraw(event.getAmount());
        destination.deposit(event.getAmount());
        return Map.of(
                "origin", Map.of(
                        "id", origin.getId(),
                        "balance", origin.getBalance()
                ),
                "destination", Map.of(
                        "id", destination.getId(),
                        "balance", destination.getBalance()
                )
        );
    }

    public void reset() {
        accounts.clear();
    }

}
