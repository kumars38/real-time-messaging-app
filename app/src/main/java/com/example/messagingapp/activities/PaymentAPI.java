package com.example.messagingapp.activities;

public class PaymentAPI {

    public static boolean sendPayment(String recipientName, double amount) {
        // simulate sending payment details to API
        //this is a verrrrrry barebones situation
        if (amount > 0) {
            // payment transaction successful
            return true;
        } else {
            // payment transaction failed
            return false;
        }
    }
}