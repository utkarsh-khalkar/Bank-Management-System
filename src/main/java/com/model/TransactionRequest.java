package com.model;

/**
 * Author:  Utkarsh Khalkar
 * Title:   Transaction Request class
 * Date:    15-02-2025
 * Time:    02:20 PM
 */
public class TransactionRequest {
    private String accountNumber;
    private String transactionType; // "deposit" or "withdraw"
    private double amount;

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
