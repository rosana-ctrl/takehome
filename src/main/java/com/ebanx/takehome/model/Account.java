package com.ebanx.takehome.model;

public class Account {

    private String id;
    private Integer balance;

    public Account(String id) {
        this.id = id;
        this.balance = 0;
    }

    public String getId() {
        return id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void deposit(Integer amount) {
        this.balance += amount;
    }

    public void withdraw(Integer amount) {
        this.balance -= amount;
    }
}
