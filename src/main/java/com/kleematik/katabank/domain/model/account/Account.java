package com.kleematik.katabank.domain.model.account;

import com.kleematik.katabank.application.logging.PrintStatement;
import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.model.transaction.TransactionType;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Account {

    private final TransactionRepository transactionRepository;
    private final PrintStatement printStatement;

    public void makeTransaction(final TransactionType transactionType, final String amount) {
        final Transaction transaction = Transaction.of(amount, transactionType);
        transactionRepository.save(transaction);
    }

    public void printStatement() {
        final var transactions = transactionRepository.findAll();
        printStatement.print(transactions);
    }

}
