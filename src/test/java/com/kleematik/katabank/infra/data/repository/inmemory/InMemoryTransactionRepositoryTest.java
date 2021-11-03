package com.kleematik.katabank.infra.data.repository.inmemory;

import com.kleematik.katabank.domain.model.account.Account;
import com.kleematik.katabank.domain.model.transaction.Money;
import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.model.transaction.TransactionType;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.infra.logging.ConsolePrintStatement;
import com.kleematik.katabank.application.logging.PrintStatement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Tests::InMemoryTransactionRepository")
class InMemoryTransactionRepositoryTest {

    private Account account;
    private TransactionRepository underTest;

    @BeforeEach
    void setUp() {
        underTest = InMemoryTransactionRepositoryImpl.of();
        final PrintStatement printStatement = ConsolePrintStatement.getInstance();

        account = Account.builder()
                .transactionRepository(underTest)
                .printStatement(printStatement)
                .build();
    }

    @AfterEach
    void tearDown() {
        account = null;
        underTest = null;
    }

    @Test
    void itShouldBeEmpty() {
        assertEquals(0, underTest.findAll().size());
    }

    @Test
    void itShouldHaveOneTransaction() {
        account.make(TransactionType.DEPOSIT, "200");
        assertEquals(1, underTest.findAll().size());
    }

    @Test
    void itShouldBeTheSameTransaction() {
        account.make(TransactionType.DEPOSIT, "200");
        final List<Transaction> transactions = underTest.findAll();
        final Transaction expectedTransaction = Transaction.builder()
                .transactionType(TransactionType.DEPOSIT)
                .amount(Money.of("200"))
                .date(LocalDateTime.now())
                .build();
        assertEquals(expectedTransaction, transactions.get(0));
    }

    @Test
    void itShouldBeNotTheSameTransaction() {
        account.make(TransactionType.WITHDRAW, "200");
        final List<Transaction> transactions = underTest.findAll();
        final Transaction expectedTransaction = Transaction.builder()
                .transactionType(TransactionType.DEPOSIT)
                .amount(Money.of("100"))
                .date(LocalDateTime.now())
                .build();
        assertNotEquals(expectedTransaction, transactions.get(0));
    }

    @Test
    void itShouldHaveMoreTransactions() {
        account.make(TransactionType.DEPOSIT, "200");
        account.make(TransactionType.WITHDRAW, "20");
        account.make(TransactionType.DEPOSIT, "100");
        account.make(TransactionType.WITHDRAW, "250");
        account.make(TransactionType.WITHDRAW, "300");
        account.make(TransactionType.DEPOSIT, "200");

        final List<Transaction> transactions = underTest.findAll();
        assertEquals(6, transactions.size());
    }
}