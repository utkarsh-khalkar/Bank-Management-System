package com.controller;

import com.model.Account;
import com.model.TransactionRequest;
import com.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Author:  Utkarsh Khalkar
 * Title:   Account Controller for adding,deleting,updating,displaying Account details
 * Date:    14-02-2025
 * Time:    010:20 PM
 */

@RestController
@RequestMapping("/api/v1/accounts")
@Slf4j
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<String> addAccountDetails(@RequestBody Account account) {
        log.info("START :: CLASS :: AccountController :: METHOD :: addAccountDetails :: ACCOUNT HOLDER NAME :: "+account.getHolderName());
        if (account.getAccountNumber() == null || account.getAccountNumber().trim().isEmpty()
                || account.getHolderName() == null || account.getHolderName().trim().isEmpty()
                || account.getAccountType() == null || account.getAccountType().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Account Details");
        }

        if (!account.getAccountNumber().matches("^[0-9]{10}$")) {
            return ResponseEntity.badRequest().body("Invalid Account Number (Must be exactly 10 digits)");
        }

        String response = accountService.addAccountDetails(account);
        if (response.equals("Account Already Exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        log.info("END :: CLASS :: AccountController :: METHOD :: addAccountDetails :: ACCOUNT HOLDER NAME :: "+account.getHolderName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccountDetails() {
        log.info("START :: CLASS :: AccountController :: METHOD :: getALLAccounts ");
        return ResponseEntity.ok(accountService.getAllAccountDetails());
    }

    @PutMapping
    public ResponseEntity<String> updateAccountDetails(@RequestBody Account account) {
        log.info("START :: CLASS :: AccountController :: METHOD :: updateAccountDetails :: ACCOUNT HOLDER NAME :: "+account.getHolderName());
        if (account.getAccountNumber() == null || account.getAccountNumber().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Account Number is required for update");
        }

        if (!account.getAccountNumber().matches("^[0-9]{10}$")) {
            return ResponseEntity.badRequest().body("Invalid Account Number format");
        }

        boolean isUpdated = accountService.updateAccountDetails(account);
        if (!isUpdated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Not Found");
        }
        log.info("END :: CLASS :: AccountController :: METHOD :: updateAccountDetails :: ACCOUNT HOLDER NAME :: "+account.getHolderName());
        return ResponseEntity.ok("Account Details Updated Successfully");
    }

    @PostMapping("/transaction")
    public ResponseEntity<String> processTransaction(@RequestBody TransactionRequest request) {
        log.info("START :: CLASS :: AccountController :: METHOD :: processTransaction :: ACCOUNT NUMBER :: "+request.getAccountNumber());
        if (request.getAccountNumber() == null || request.getAccountNumber().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Account Number");
        }

        if (!request.getAccountNumber().matches("^[0-9]{10}$")) {
            return ResponseEntity.badRequest().body("Invalid Account Number format");
        }

        if (request.getTransactionType() == null || request.getTransactionType().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Transaction Type is required");
        }

        if (request.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than zero");
        }

        String response = accountService.processTransaction(request.getAccountNumber(), request.getTransactionType(), request.getAmount());
        if (response.equals("Account Not Found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        log.info("END :: CLASS :: AccountController :: METHOD :: processTransaction :: ACCOUNT NUMBER :: "+request.getAccountNumber());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> removeAccountDetails(@PathVariable String accountNumber) {
        log.info("START :: CLASS :: AccountController :: METHOD :: removeAccountDetails  :: ACCOUNT NUMBER :: "+accountNumber);
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Account Number");
        }

        if (!accountNumber.matches("^[0-9]{10}$")) {
            return ResponseEntity.badRequest().body("Invalid Account Number format");
        }

        boolean isDeleted = accountService.removeAccountDetails(accountNumber);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Not Found");
        }
        log.info("END :: CLASS :: AccountController :: METHOD :: removeAccountDetails  :: ACCOUNT NUMBER :: "+accountNumber);
        return ResponseEntity.ok("Account Deleted Successfully");
    }
}
