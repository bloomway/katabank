package com.kleematik.katabank.domain.model.account;

import com.kleematik.katabank.application.common.DateTimeProvider;
import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.domain.services.print.ConsolePrintStatement;
import com.kleematik.katabank.domain.services.print.PrintStatement;
import com.kleematik.katabank.infra.datetime.SystemDateTimeProviderImpl;
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
        final DateTimeProvider dateTimeProvider = SystemDateTimeProviderImpl.getInstance();
        final PrintStatement printStatement = ConsolePrintStatement.getInstance();

        //transactionRepositoryMock = Mockito.mock(TransactionRepository.class);

        underTest = Account.builder()
                .transactionRepository(transactionRepositoryMock)
                .dateTimeProvider(dateTimeProvider)
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
        underTest.make(Transaction.Kind.DEPOSIT, "200");
        // verifie que la transaction a bien eu lieu
        verify(transactionRepositoryMock).save(any(Transaction.class));
    }

    @Test
    void itShouldMakeTwoTransaction() {
        underTest.make(Transaction.Kind.DEPOSIT, "200");
        underTest.make(Transaction.Kind.WITHDRAW, "200");
        // verifie que la transaction a bien eu lieu
        verify(transactionRepositoryMock, times(2)).save(any(Transaction.class));
    }
}