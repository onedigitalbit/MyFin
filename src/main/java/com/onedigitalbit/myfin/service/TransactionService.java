package com.onedigitalbit.myfin.service;

import com.onedigitalbit.myfin.entity.Transaction;
import com.onedigitalbit.myfin.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public Transaction updateTransaction(Long id, Transaction transaction) {
        Transaction existingTransaction = getTransactionById(id);
        existingTransaction.setAmount(transaction.getAmount());
        existingTransaction.setTransactionType(transaction.getTransactionType());
        existingTransaction.setDescription(transaction.getDescription());
        existingTransaction.setTransactionDate(transaction.getTransactionDate());
        existingTransaction.setAccount(transaction.getAccount());
        existingTransaction.setCategory(transaction.getCategory());
        existingTransaction.setSubCategory(transaction.getSubCategory());
        existingTransaction.setLinkedLoan(transaction.getLinkedLoan());
        return transactionRepository.save(existingTransaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}