package com.onedigitalbit.myfin.repository;

import com.onedigitalbit.myfin.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {}
