package com.controller;

import com.model.Loan;
import com.model.LoanPaymentRequest;
import com.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loans")
/**
 * Author:  Utkarsh Khalkar
 * Title:   Loan Services Class
 * Date:    15-02-2025
 * Time:    02:30 PM
 */
@Slf4j
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<String> applyForLoan(@RequestBody Loan loan) {
        log.info("START :: CLASS :: LoanController :: METHOD :: applyForLoan  :: ACCOUNT NUMBER :: "+loan.getAccountNumber());
        if (loan.getAccountNumber() == null || loan.getAccountNumber().trim().isEmpty()
                || loan.getLoanAmount() <= 0 || loan.getInterestRate() <= 0) {
            return ResponseEntity.badRequest().body("Invalid Loan Details");
        }

        String response = loanService.applyForLoan(loan);
        if (response.equals("Loan Already Exists for this Account")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        log.info("END :: CLASS :: LoanController :: METHOD :: applyForLoan  :: ACCOUNT NUMBER :: "+loan.getAccountNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Object> getLoanDetails(@PathVariable String accountNumber) {
        log.info("START :: CLASS :: LoanController :: METHOD :: getLoanDetails  :: ACCOUNT NUMBER :: "+accountNumber);
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Account Number");
        }

        Loan loan = loanService.getLoanByAccount(accountNumber);
        if (loan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Active Loan Found");
        }
        log.info("END :: CLASS :: LoanController :: METHOD :: getLoanDetails  :: ACCOUNT NUMBER :: "+accountNumber);
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/payment")
    public ResponseEntity<String> makeLoanPayment(@RequestBody LoanPaymentRequest request) {
        log.info("START :: CLASS :: LoanController :: METHOD :: makeLoanPayment  :: ACCOUNT NUMBER :: "+request.getAccountNumber());
        if (request.getAccountNumber() == null || request.getAccountNumber().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Account Number");
        }
        if (request.getPaymentAmount() <= 0) {
            return ResponseEntity.badRequest().body("Payment Amount must be greater than zero");
        }

        String response = loanService.makeLoanPayment(request.getAccountNumber(), request.getPaymentAmount());
        if (response.equals("No Active Loan Found for this Account")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        if (response.equals("Payment exceeds remaining loan amount")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        log.info("END :: CLASS :: LoanController :: METHOD :: makeLoanPayment  :: ACCOUNT NUMBER :: "+request.getAccountNumber());
        return ResponseEntity.ok(response);
    }
}
