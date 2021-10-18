package com.kleematik.katabank.infra.data.repository.inmemory;

import com.kleematik.katabank.domain.model.account.Account;
import com.kleematik.katabank.domain.model.transaction.Money;
import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.domain.services.print.ConsolePrintStatement;
import com.kleematik.katabank.domain.services.print.PrintStatement;
import com.kleematik.katabank.infra.datetime.SystemDateTimeProviderImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Tests::InMemoryTransactionRepository")
class InMemoryTransactionRepositoryTest {

    private Account account;
    private final SystemDateTimeProviderImpl dateTimeProvider = SystemDateTimeProviderImpl.getInstance();
    private TransactionRepository underTest;

    @BeforeEach
    void setUp() {
        underTest = InMemoryTransactionRepositoryImpl.of();
        final PrintStatement printStatement = ConsolePrintStatement.getInstance();

        account = Account.builder()
                .transactionRepository(underTest)
                .dateTimeProvider(dateTimeProvider)
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
        final int size = underTest.findAll().orElse(new ArrayList<>()).size();
        assertEquals(0, size);
    }

    @Test
    void itShouldHaveOneTransaction() {
        account.make(Transaction.Kind.DEPOSIT, "200");

        final List<Transaction> transactions = underTest.findAll().orElse(new ArrayList<>());

        assertEquals(1, transactions.size());

        final Transaction expectedTransaction = Transaction.builder()
                .kind(Transaction.Kind.DEPOSIT)
                .amount(Money.of("200"))
                .date(dateTimeProvider.now())
                .build();

        assertEquals(expectedTransaction, transactions.get(0));
    }

    @Test
    void itShouldBeTheSameTransaction() {
        account.make(Transaction.Kind.DEPOSIT, "200");

        final List<Transaction> transactions = underTest.findAll().orElse(new ArrayList<>());

        final Transaction expectedTransaction = Transaction.builder()
                .kind(Transaction.Kind.DEPOSIT)
                .amount(Money.of("200"))
                .date(dateTimeProvider.now())
                .build();

        assertEquals(expectedTransaction, transactions.get(0));
    }

    @Test
    void itShouldBeNotTheSameTransaction() {
        account.make(Transaction.Kind.WITHDRAW, "200");

        final List<Transaction> transactions = underTest.findAll().orElse(new ArrayList<>());

        final Transaction expectedTransaction = Transaction.builder()
                .kind(Transaction.Kind.DEPOSIT)
                .amount(Money.of("200"))
                .date(dateTimeProvider.now())
                .build();

        assertNotEquals(expectedTransaction, transactions.get(0));
    }

    @Test
    void itShouldHaveMoreTransactions() {
        account.make(Transaction.Kind.DEPOSIT, "200");
        account.make(Transaction.Kind.WITHDRAW, "200");
        account.make(Transaction.Kind.DEPOSIT, "200");
        account.make(Transaction.Kind.WITHDRAW, "200");
        account.make(Transaction.Kind.WITHDRAW, "200");
        account.make(Transaction.Kind.DEPOSIT, "200");

        final List<Transaction> transactions = underTest.findAll().orElse(new ArrayList<>());
        assertEquals(6, transactions.size());
    }
}