package com.ebanx.takehome.validator;

import com.ebanx.takehome.model.Event;

import org.springframework.stereotype.Service;

@Service
public class EventValidator {

    public static boolean validate(Event event) {
        return switch (event.getType()) {
            case deposit -> validateDeposit(event);
            case withdraw -> validateWithdraw(event);
            case transfer -> validateTransfer(event);
            default -> false;
        };
    }

    public static void validateAccountId(String accountId) throws Exception {
        if (accountId == null ||
                accountId.isBlank() ||
                accountId.contains(" ")
        ) {
            throw new Exception("invalid accountId");
        }
    }

    public static void validateAmount(Integer amount) throws Exception {
        if (amount == null ||
                amount <= 0
        ) {
            throw new Exception("invalid amount");
        }
    }

    private static boolean validateDeposit(Event event) {
        try {
            validateAccountId(event.getDestination());
            validateAmount(event.getAmount());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean validateWithdraw(Event event) {
        try {
            validateAccountId(event.getOrigin());
            validateAmount(event.getAmount());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean validateTransfer(Event event) {
        try {
            validateAccountId(event.getOrigin());
            validateAccountId(event.getDestination());
            validateAmount(event.getAmount());
            if (event.getOrigin().equals(event.getDestination())) {
                throw new Exception("invalid destination");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
