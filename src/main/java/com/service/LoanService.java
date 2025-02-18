package com.service;

import com.model.Loan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:  Utkarsh Khalkar
 * Title:   Loan Services Class
 * Date:    15-02-2025
 * Time:    02:20 PM
 */
@Service
@Slf4j
public class LoanService {
    private final List<Loan> loans = new ArrayList<>();

    public String applyForLoan(Loan loan) {
        log.info("START :: CLASS :: LoanService :: METHOD :: applyForLoan  :: ACCOUNT NUMBER :: "+loan.getAccountNumber());
        if (loanExists(loan.getAccountNumber())) {
            return "Loan Already Exists for this Account";
        }

        loan.setRemainingLoanAmount(loan.getLoanAmount());
        loans.add(loan);
        log.info("END :: CLASS :: LoanService :: METHOD :: applyForLoan  :: ACCOUNT NUMBER :: "+loan.getAccountNumber());
        return "Loan Applied Successfully";
    }

    public Loan getLoanByAccount(String accountNumber) {
        log.info("START :: CLASS :: LoanService :: METHOD :: getLoanByAccount  :: ACCOUNT NUMBER :: "+accountNumber);
        for (Loan loan : loans) {
            if (loan.getAccountNumber().equals(accountNumber)) {
                return loan;
            }
        }
        log.info("END :: CLASS :: LoanService :: METHOD :: getLoanByAccount  :: ACCOUNT NUMBER :: "+accountNumber);
        return null;
    }

    public String makeLoanPayment(String accountNumber, double paymentAmount) {
        log.info("START :: CLASS :: LoanService :: METHOD :: makeLoanPayment  :: ACCOUNT NUMBER :: "+accountNumber);
        Loan loan = getLoanByAccount(accountNumber);
        if (loan == null) {
            return "No Active Loan Found for this Account";
        }
        if (paymentAmount <= 0) {
            return "Payment amount must be greater than zero";
        }
        if (paymentAmount > loan.getRemainingLoanAmount()) {
            return "Payment exceeds remaining loan amount";
        }

        // Deduct payment from the remaining loan amount
        loan.setRemainingLoanAmount(loan.getRemainingLoanAmount() - paymentAmount);
        log.info("END :: CLASS :: LoanService :: METHOD :: makeLoanPayment  :: ACCOUNT NUMBER :: "+accountNumber);

        return "Payment Successful, Remaining Loan Amount: " + loan.getRemainingLoanAmount();
    }

    private boolean loanExists(String accountNumber) {
        log.info("START :: CLASS :: LoanService :: METHOD :: loanExists  :: ACCOUNT NUMBER :: "+accountNumber);
        for (Loan loan : loans) {
            if (loan.getAccountNumber().equals(accountNumber)) {
                return true;
            }
        }
        log.info("END  :: CLASS :: LoanService :: METHOD :: loanExists  :: ACCOUNT NUMBER :: "+accountNumber);
        return false;
    }
}
