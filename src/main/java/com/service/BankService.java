package com.service;

import com.model.Bank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Author:  Utkarsh Khalkar
 * Title:   Bank Services for adding,deleting,updating,displaying bank details
 * Date:    14-02-2025
 * Time:    09:20 PM
 */
@Service
@Slf4j
public class BankService {
    private final Set<Bank> banks = new HashSet<>();

    public Set<Bank> getAllBankDetails() {
        return Collections.unmodifiableSet(banks);
    }

    public String addBankDetails(Bank bank) {
        log.info("START :: CLASS :: BankService :: METHOD :: addBankDetails  :: BANK NAME :: "+bank.getBankName());
        if (bankExists(bank.getIfscCode())) {
            return "Bank already exists";
        }
        banks.add(bank);
        log.info("END :: CLASS :: BankService :: METHOD :: addBankDetails  :: BANK NAME :: "+bank.getBankName());
        return "Bank added successfully";
    }

    public boolean updateBankDetails(Bank bank) {
        log.info("START :: CLASS :: BankService :: METHOD :: updateBankDetails  :: BANK NAME :: "+bank.getBankName());
        for (Bank existingBank : banks) {
            if (existingBank.getIfscCode().equals(bank.getIfscCode())) {
                banks.remove(existingBank);
                banks.add(bank);
                return true;
            }
        }
        log.info("END :: CLASS :: BankService :: METHOD :: updateBankDetails  :: BANK NAME :: "+bank.getBankName());
        return false;
    }

    public boolean removeBankDetails(String ifscCode) {
        log.info("START :: CLASS :: BankService :: METHOD :: removeBankDetails  :: IFSC CODE :: "+ifscCode);
        Bank bankToRemove = null;
        for (Bank bank : banks) {
            if (bank.getIfscCode().equals(ifscCode)) {
                bankToRemove = bank;
                break;
            }
        }
        if (bankToRemove != null) {
            banks.remove(bankToRemove);
            return true;
        }
        log.info("END :: CLASS :: BankService :: METHOD :: removeBankDetails  :: IFSC CODE :: "+ifscCode);
        return false;
    }

    private boolean bankExists(String ifscCode) {
        log.info("START :: CLASS :: BankService :: METHOD :: bankExists  :: IFSC CODE :: "+ifscCode);
        for (Bank bank : banks) {
            if (bank.getIfscCode().equals(ifscCode)) {
                return true;
            }
        }
        log.info("END :: CLASS :: BankService :: METHOD :: bankExists  :: IFSC CODE :: "+ifscCode);
        return false;
    }
}
