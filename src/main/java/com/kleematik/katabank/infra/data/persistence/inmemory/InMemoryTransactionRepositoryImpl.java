package com.kleematik.katabank.infra.data.persistence.inmemory;

import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(staticName = "of")
public class InMemoryTransactionRepositoryImpl implements TransactionRepository
{
    @Override
    public void save(Transaction transaction)
    {
    }

    @Override
    public Optional<List<Transaction>> findAll()
    {
        return Optional.empty();
    }
}
