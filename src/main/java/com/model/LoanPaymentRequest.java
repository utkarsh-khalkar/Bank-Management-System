package com.model;
/**
 * Author:  Utkarsh Khalkar
 * Title:   Loan Request class
 * Date:    15-02-2025
 * Time:    03:20 PM
 */
public class LoanPaymentRequest {
    private String accountNumber;
    private double paymentAmount;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
