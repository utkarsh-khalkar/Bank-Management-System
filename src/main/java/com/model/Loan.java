package com.model;
/**
 * Author:  Utkarsh Khalkar
 * Title:   Loan Model Class
 * Date:    15-02-2025
 * Time:    4:20 PM
 */
public class Loan {
    private String accountNumber;
    private double loanAmount;
    private double interestRate;
    private double remainingLoanAmount;



    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getRemainingLoanAmount() {
        return remainingLoanAmount;
    }

    public void setRemainingLoanAmount(double remainingLoanAmount) {
        this.remainingLoanAmount = remainingLoanAmount;
    }
}
