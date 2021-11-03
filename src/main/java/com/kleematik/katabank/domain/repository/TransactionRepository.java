package com.kleematik.katabank.domain.repository;

import com.kleematik.katabank.domain.model.transaction.Transaction;

import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);
    List<Transaction> findAll();
}
