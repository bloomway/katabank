package com.kleematik.katabank.domain.model.account;

import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.model.transaction.TransactionType;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.infra.logging.ConsolePrintStatement;
import com.kleematik.katabank.application.logging.PrintStatement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests::Account")
class AccountTest {

    @Mock
    private TransactionRepository transactionRepositoryMock;
    private Account underTest;

    @BeforeEach
    public void before() {
        final PrintStatement printStatement = ConsolePrintStatement.getInstance();

        underTest = Account.builder()
                .transactionRepository(transactionRepositoryMock)
                .printStatement(printStatement)
                .build();
    }

    @AfterEach
    public void tearDown() {
        underTest = null;
    }

    @Test
    void itShouldMakeZeroTransaction() {
        verifyNoMoreInteractions(transactionRepositoryMock);
    }

    @Test
    void itShouldMakeOneTransaction() {
        underTest.make(TransactionType.DEPOSIT, "200");
        verify(transactionRepositoryMock).save(any(Transaction.class));
    }

    @Test
    void itShouldMakeTwoTransaction() {
        underTest.make(TransactionType.DEPOSIT, "200");
        underTest.make(TransactionType.WITHDRAW, "200");
        verify(transactionRepositoryMock, times(2)).save(any(Transaction.class));
    }
}