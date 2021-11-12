package com.kleematik.katabank.infra.di;

import com.kleematik.katabank.application.logging.PrintStatement;
import com.kleematik.katabank.domain.model.account.Account;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.infra.data.repository.inmemory.InMemoryTransactionRepositoryImpl;
import com.kleematik.katabank.infra.logging.ConsolePrintStatement;

public final class Factory {

    private Factory() {}

    public static TransactionRepository createTransactionRepository() {
        return InMemoryTransactionRepositoryImpl.getInstance();
    }

    public static PrintStatement createConsolePrintStatement() {
        return ConsolePrintStatement.getInstance();
    }

    public static Account createAccount(TransactionRepository repository, PrintStatement printStatement) {
        return Account.builder()
                .transactionRepository(repository)
                .printStatement(printStatement)
                .build();
    }
}
