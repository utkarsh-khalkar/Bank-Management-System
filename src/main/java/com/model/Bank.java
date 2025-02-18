package com.model;
import java.util.Objects;

/**
 * Author:  Utkarsh Khalkar
 * Title:   Bank Model class f
 * Date:    14-02-2025
 * Time:    09:50 AM
 */

public class Bank {
    private String bankName;
    private String branchName;
    private String ifscCode;

    // Getters and Setters
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    // Equals and HashCode for object comparison in Set
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Bank bank = (Bank) obj;
        return Objects.equals(ifscCode, bank.ifscCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ifscCode);
    }
}
