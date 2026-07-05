package com.ahamed.fintracker.repository;

import com.ahamed.fintracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}