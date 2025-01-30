package com.onedigitalbit.myfin.service;

import com.onedigitalbit.myfin.entity.Bank;
import com.onedigitalbit.myfin.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank createBank(Bank bank) {
        return bankRepository.save(bank);
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public Bank getBankById(Long id) {
        return bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank not found"));
    }

    public Bank updateBank(Long id, Bank bank) {
        Bank existingBank = getBankById(id);
        existingBank.setBankName(bank.getBankName());
        existingBank.setAccountNumber(bank.getAccountNumber());
        existingBank.setBranchName(bank.getBranchName());
        existingBank.setIfscCode(bank.getIfscCode());
        return bankRepository.save(existingBank);
    }

    public void deleteBank(Long id) {
        bankRepository.deleteById(id);
    }
}