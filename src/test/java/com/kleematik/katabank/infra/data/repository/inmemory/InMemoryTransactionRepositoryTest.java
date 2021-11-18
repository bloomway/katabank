package com.kleematik.katabank.infra.data.repository.inmemory;

import com.kleematik.katabank.application.logging.PrintStatement;
import com.kleematik.katabank.domain.model.account.Account;
import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.model.transaction.TransactionType;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.infra.logging.ConsolePrintStatement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        account.makeTransaction(TransactionType.DEPOSIT, "200.0");
        assertEquals(1, underTest.findAll().size());
    }

    @Test
    void itShouldBeTheSameTransaction() {
        account.makeTransaction(TransactionType.DEPOSIT, "200.0");
        final List<Transaction> transactions = underTest.findAll();
        final Transaction expectedTransaction = Transaction.of("200.0", TransactionType.DEPOSIT);
        assertEquals(expectedTransaction, transactions.get(0));
    }

    @Test
    void itShouldBeNotTheSameTransaction() {
        account.makeTransaction(TransactionType.WITHDRAW, "200.0");
        final List<Transaction> transactions = underTest.findAll();
        final Transaction expectedTransaction = Transaction.of("100.0", TransactionType.DEPOSIT);
        assertNotEquals(expectedTransaction, transactions.get(0));
    }

    @Test
    void itShouldHaveMoreTransactions() {
        account.makeTransaction(TransactionType.DEPOSIT, "200.0");
        account.makeTransaction(TransactionType.WITHDRAW, "20.5");
        account.makeTransaction(TransactionType.DEPOSIT, "100.0");
        account.makeTransaction(TransactionType.WITHDRAW, "250.25");
        account.makeTransaction(TransactionType.WITHDRAW, "350.75");
        account.makeTransaction(TransactionType.DEPOSIT, "200.0");

        final List<Transaction> transactions = underTest.findAll();
        assertEquals(6, transactions.size());
    }
}