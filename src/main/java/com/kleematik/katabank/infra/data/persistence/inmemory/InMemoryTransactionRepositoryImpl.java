package com.kleematik.katabank.infra.data.persistence.inmemory;

import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@NoArgsConstructor(staticName = "of")
public class InMemoryTransactionRepositoryImpl implements TransactionRepository
{
    private final List<Transaction> CACHES = new ArrayList<>();

    @Override
    public void save(Transaction transaction)
    {
        CACHES.add(transaction);
    }

    @Override
    public Optional<List<Transaction>> findAll()
    {
        return Optional.of(new ArrayList<>(CACHES));
    }
}
