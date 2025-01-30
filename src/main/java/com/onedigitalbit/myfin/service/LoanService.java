package com.onedigitalbit.myfin.service;

import com.onedigitalbit.myfin.entity.Loan;
import com.onedigitalbit.myfin.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    public Loan updateLoan(Long id, Loan loan) {
        Loan existingLoan = getLoanById(id);
        existingLoan.setLoanType(loan.getLoanType());
        existingLoan.setBorrowerOrLenderName(loan.getBorrowerOrLenderName());
        existingLoan.setAmount(loan.getAmount());
        existingLoan.setDescription(loan.getDescription());
        existingLoan.setDueDate(loan.getDueDate());
        existingLoan.setStatus(loan.getStatus());
        existingLoan.setRemainingBalance(loan.getRemainingBalance());
        return loanRepository.save(existingLoan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}