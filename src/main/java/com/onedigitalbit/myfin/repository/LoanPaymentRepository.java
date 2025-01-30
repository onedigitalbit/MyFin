package com.onedigitalbit.myfin.repository;

import com.onedigitalbit.myfin.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Long> {}
