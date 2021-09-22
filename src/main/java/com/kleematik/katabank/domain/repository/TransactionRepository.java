package com.kleematik.katabank.domain.repository;

import com.kleematik.katabank.domain.model.transaction.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository
{
    void save(Transaction transaction);
    Optional<List<Transaction>> findAll();
}
