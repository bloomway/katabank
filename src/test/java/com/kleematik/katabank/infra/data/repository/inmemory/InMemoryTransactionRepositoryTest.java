package com.kleematik.katabank.infra.data.repository.inmemory;

import com.kleematik.katabank.application.logging.PrintStatement;
import com.kleematik.katabank.domain.model.account.Account;
import com.kleematik.katabank.domain.model.transaction.Money;
import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.model.transaction.TransactionType;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.infra.logging.ConsolePrintStatement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        account.make(TransactionType.DEPOSIT, "200.0");
        assertEquals(1, underTest.findAll().size());
    }

    @Test
    void itShouldBeTheSameTransaction() {
        account.make(TransactionType.DEPOSIT, "200.0");
        final List<Transaction> transactions = underTest.findAll();
        final Transaction expectedTransaction = Transaction.builder()
                .transactionType(TransactionType.DEPOSIT)
                .money(Money.of("200.0"))
                .date(LocalDateTime.now())
                .build();
        assertEquals(expectedTransaction, transactions.get(0));
    }

    @Test
    void itShouldBeNotTheSameTransaction() {
        account.make(TransactionType.WITHDRAW, "200.0");
        final List<Transaction> transactions = underTest.findAll();
        final Transaction expectedTransaction = Transaction.builder()
                .transactionType(TransactionType.DEPOSIT)
                .money(Money.of("100.0"))
                .date(LocalDateTime.now())
                .build();
        assertNotEquals(expectedTransaction, transactions.get(0));
    }

    @Test
    void itShouldHaveMoreTransactions() {
        account.make(TransactionType.DEPOSIT, "200.0");
        account.make(TransactionType.WITHDRAW, "20.5");
        account.make(TransactionType.DEPOSIT, "100.0");
        account.make(TransactionType.WITHDRAW, "250.25");
        account.make(TransactionType.WITHDRAW, "350.75");
        account.make(TransactionType.DEPOSIT, "200.0");

        final List<Transaction> transactions = underTest.findAll();
        assertEquals(6, transactions.size());
    }
}