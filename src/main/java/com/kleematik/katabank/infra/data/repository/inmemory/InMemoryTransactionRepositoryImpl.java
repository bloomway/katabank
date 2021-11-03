package com.kleematik.katabank.infra.data.repository.inmemory;

import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InMemoryTransactionRepositoryImpl implements TransactionRepository {

    private final List<Transaction> CACHES = new ArrayList<>();

    // used for production
    public static InMemoryTransactionRepositoryImpl getInstance() {
        return Holder.INSTANCE;
    }

    // used for tests only
    public static InMemoryTransactionRepositoryImpl of() {
        return new InMemoryTransactionRepositoryImpl();
    }

    @Override
    public void save(Transaction transaction)
    {
        CACHES.add(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        return !CACHES.isEmpty() ? new ArrayList<>(CACHES) : new ArrayList<>();
    }

    private static class Holder {
        public static final InMemoryTransactionRepositoryImpl INSTANCE = new InMemoryTransactionRepositoryImpl();
    }
}
