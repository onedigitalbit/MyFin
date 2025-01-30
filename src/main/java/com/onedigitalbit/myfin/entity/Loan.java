package com.onedigitalbit.myfin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loanId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanType loanType;

    @Column(nullable = false)
    private String borrowerOrLenderName;

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.PENDING;

    @Column(nullable = false)
    private BigDecimal remainingBalance;

    @Column(updatable = false)
    private LocalDate createdAt = LocalDate.now();

    public enum LoanType { LENT, BORROWED }
    public enum LoanStatus { PENDING, PAID }
}
