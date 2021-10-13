package com.kleematik.katabank.infra.data.persistence.inmemory;

import com.kleematik.katabank.domain.model.account.Account;
import com.kleematik.katabank.domain.model.transaction.Money;
import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.infra.datetime.SystemDateTimeProviderImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class InMemoryTransactionRepositoryTest {

    private Account account;
    @Autowired
    private SystemDateTimeProviderImpl dateTimeProvider;
    private TransactionRepository underTest;

    @BeforeEach
    void setUp() {
        underTest = InMemoryTransactionRepositoryImpl.of();
        account = Account.builder()
                .transactionRepository(underTest)
                .dateTimeProvider(dateTimeProvider)
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
        account.deposit("200");

        final List<Transaction> transactions = underTest.findAll().orElse(new ArrayList<>());

        assertEquals(1, transactions.size());

        final Transaction expectedTransaction = Transaction.builder()
                .type(Transaction.Type.DEPOSIT)
                .amount(Money.of("200"))
                .date(dateTimeProvider.now())
                .build();

        assertEquals(expectedTransaction, transactions.get(0));
    }

    @Test
    void itShouldBeTheSameTransaction() {
        account.deposit("200");

        final List<Transaction> transactions = underTest.findAll().orElse(new ArrayList<>());

        final Transaction expectedTransaction = Transaction.builder()
                .type(Transaction.Type.DEPOSIT)
                .amount(Money.of("200"))
                .date(dateTimeProvider.now())
                .build();

        assertEquals(expectedTransaction, transactions.get(0));
    }

    @Test
    void itShouldBeNotTheSameTransaction() {
        account.withdraw("200");

        final List<Transaction> transactions = underTest.findAll().orElse(new ArrayList<>());

        final Transaction expectedTransaction = Transaction.builder()
                .type(Transaction.Type.DEPOSIT)
                .amount(Money.of("200"))
                .date(dateTimeProvider.now())
                .build();

        assertNotEquals(expectedTransaction, transactions.get(0));
    }

    @Test
    void itShouldHaveMoreTransactions() {
        account.deposit("200");
        account.withdraw("150");
        account.deposit( "200");
        account.withdraw( "150");
        account.withdraw( "150");
        account.deposit( "200");

        final List<Transaction> transactions = underTest.findAll().orElse(new ArrayList<>());
        assertEquals(6, transactions.size());
    }
}