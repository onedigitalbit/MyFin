package com.onedigitalbit.myfin.service;

import com.onedigitalbit.myfin.entity.LoanPayment;
import com.onedigitalbit.myfin.repository.LoanPaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanPaymentService {
    private final LoanPaymentRepository loanPaymentRepository;

    public LoanPaymentService(LoanPaymentRepository loanPaymentRepository) {
        this.loanPaymentRepository = loanPaymentRepository;
    }

    public LoanPayment createLoanPayment(LoanPayment loanPayment) {
        return loanPaymentRepository.save(loanPayment);
    }

    public List<LoanPayment> getAllLoanPayments() {
        return loanPaymentRepository.findAll();
    }

    public LoanPayment getLoanPaymentById(Long id) {
        return loanPaymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan Payment not found"));
    }

    public LoanPayment updateLoanPayment(Long id, LoanPayment loanPayment) {
        LoanPayment existingLoanPayment = getLoanPaymentById(id);
        existingLoanPayment.setPaymentAmount(loanPayment.getPaymentAmount());
        existingLoanPayment.setPaymentDate(loanPayment.getPaymentDate());
        return loanPaymentRepository.save(existingLoanPayment);
    }

    public void deleteLoanPayment(Long id) {
        loanPaymentRepository.deleteById(id);
    }
}