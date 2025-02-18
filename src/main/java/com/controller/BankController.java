package com.controller;

import com.model.Bank;
import com.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

/**
 * Author:  Utkarsh Khalkar
 * Title:   Bank Controller for adding,deleting,updating,displaying bank details
 * Date:    14-02-2025
 * Time:    09:00 PM
 */
@RestController
@RequestMapping("/api/v1/banks")
@Slf4j

public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping
    public ResponseEntity<String> addBankDetails(@RequestBody Bank bank) {

        log.info("START :: CLASS :: BankController :: METHOD :: addBankDetails :: BANK NAME :: "+bank.getBankName());
        // Input validation
        if (bank.getBankName() == null || bank.getBankName().trim().isEmpty()
                || bank.getBranchName() == null || bank.getBranchName().trim().isEmpty()
                || bank.getIfscCode() == null || bank.getIfscCode().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Bank Details");
        }

        if (!bank.getIfscCode().matches("^[A-Za-z]{4}[0-9]{7}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid IFSC Code (Must be exactly 12 characters: 4 letters followed by 7 digits)");
        }

        String response = bankService.addBankDetails(bank);
        if (response.equals("Bank already exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        log.info("END  :: CLASS :: BankController :: METHOD :: addBankDetails :: BANK NAME :: "+bank.getBankName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<Set<Bank>> getAllBankDetails() {
        log.info("START :: CLASS :: BankController :: METHOD :: getAllBankDetails ");
        Set<Bank> banks = bankService.getAllBankDetails();
        log.info("START :: CLASS :: BankController :: METHOD :: getAllBankDetails :: Size :: "+banks.size());
        return ResponseEntity.ok(banks);

    }

    @PutMapping
    public ResponseEntity<String> updateBankDetails(@RequestBody Bank bank) {
        log.info("START :: CLASS :: BankController :: METHOD :: updateBankDetails :: BANK NAME :: "+bank.getBankName());
        if (bank.getIfscCode() == null || bank.getIfscCode().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IFSC Code is required for update");
        }

        if (!bank.getIfscCode().matches("^[A-Za-z]{4}[0-9]{7}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid IFSC Code format");
        }

        boolean isUpdated = bankService.updateBankDetails(bank);
        if (!isUpdated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank Not Found");
        }
        log.info("END :: CLASS :: BankController :: METHOD :: updateBankDetails :: BANK NAME :: "+bank.getBankName());
        return ResponseEntity.ok("Bank Details Updated Successfully");
    }

    @DeleteMapping("/{ifscCode}")
    public ResponseEntity<String> removeBankDetails(@PathVariable String ifscCode) {
        log.info("START :: CLASS :: BankController :: METHOD :: removeBankDetails :: IFSC CODE :: "+ifscCode);
        if (ifscCode == null || ifscCode.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid IFSC Code");
        }

        if (!ifscCode.matches("^[A-Za-z]{4}[0-9]{7}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid IFSC Code ");
        }

        boolean isDeleted = bankService.removeBankDetails(ifscCode);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank Not Found");
        }
        log.info("END :: CLASS :: BankController :: METHOD :: removeBankDetails :: IFSC CODE :: "+ifscCode);
        return ResponseEntity.ok("Bank Deleted Successfully");
    }
}
