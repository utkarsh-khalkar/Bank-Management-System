package com.service;

import com.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:  Utkarsh Khalkar
 * Title:   Account Service Class for adding,deleting,updating,displaying Account details
 * Date:    14-02-2025
 * Time:    10:36 PM
 */
@Service
@Slf4j
public class AccountService {
    private final List<Account> accounts = new ArrayList<>();

    public String addAccountDetails(Account account) {
        log.info("START :: CLASS :: AccountService :: METHOD :: addAccountDetails  :: ACCOUNT NUMBER :: "+account.getAccountNumber());
        if (accountExists(account.getAccountNumber())) {
            return "Account Already Exists";
        }
        accounts.add(account);
        log.info("END  :: CLASS :: AccountService :: METHOD :: addAccountDetails  :: ACCOUNT NUMBER :: "+account.getAccountNumber());
        return "Account Added Successfully";
    }

    public List<Account> getAllAccountDetails() {
        log.info("START :: CLASS :: AccountService :: METHOD :: getAccountDetails  ");
        return new ArrayList<>(accounts);
    }

    public boolean updateAccountDetails(Account account) {
        log.info("START :: CLASS :: AccountService :: METHOD :: updateAccountDetails  :: ACCOUNT NUMBER :: "+account.getAccountNumber());
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(account.getAccountNumber())) {
                accounts.set(i, account);
                return true;
            }
        }
        log.info("END :: CLASS :: AccountService :: METHOD :: updateAccountDetails  :: ACCOUNT NUMBER :: "+account.getAccountNumber());
        return false;
    }

    public boolean removeAccountDetails(String accountNumber) {
        log.info("START :: CLASS :: AccountService :: METHOD ::  removeAccountDetails  :: ACCOUNT NUMBER :: "+accountNumber);
        return accounts.removeIf(account -> account.getAccountNumber().equals(accountNumber));
    }

    public String processTransaction(String accountNumber, String transactionType, double amount) {
        log.info("START :: CLASS :: AccountService :: METHOD :: processTransaction  :: ACCOUNT NUMBER :: "+accountNumber);
        Account account = getAccountByNumber(accountNumber);

        if (account == null) {
            return "Account Not Found";
        }

        if ("deposit".equalsIgnoreCase(transactionType)) {
            if (amount <= 0) {
                return "Deposit amount must be greater than zero";
            }
            account.setBalance(account.getBalance() + amount);
            return "Deposit Successful, Updated Balance: " + account.getBalance();
        }

        if ("withdraw".equalsIgnoreCase(transactionType)) {
            if (amount <= 0) {
                return "Withdrawal amount must be greater than zero";
            }
            if (amount > account.getBalance()) {
                return "Insufficient Balance";
            }
            account.setBalance(account.getBalance() - amount);
            return "Withdrawal Successful, Updated Balance: " + account.getBalance();
        }
        log.info("END :: CLASS :: AccountService :: METHOD :: processTransaction  :: ACCOUNT NUMBER :: "+accountNumber);
        return "Invalid Transaction Type";
    }

    private Account getAccountByNumber(String accountNumber) {
        log.info("START :: CLASS :: AccountService :: METHOD :: getAccountByNumber  :: ACCOUNT NUMBER :: "+accountNumber);
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        log.info("END :: CLASS :: AccountService :: METHOD :: getAccountByNumber  :: ACCOUNT NUMBER :: "+accountNumber);
        return null;
    }

    private boolean accountExists(String accountNumber) {
        log.info("START :: CLASS :: AccountService :: METHOD :: accountExists  :: ACCOUNT NUMBER :: "+accountNumber);
        return accounts.stream().anyMatch(account -> account.getAccountNumber().equals(accountNumber));
    }
}
