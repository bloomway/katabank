package com.kleematik.katabank.domain.model.account;

import com.kleematik.katabank.application.common.DateTimeProvider;
import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountTest
{
    @Mock
    private TransactionRepository transactionRepositoryMock;

    @Autowired
    private DateTimeProvider dateTimeProvider;

    private Account underTest;

    @BeforeEach
    public void before() {
        underTest = Account.builder()
                .transactionRepository(transactionRepositoryMock)
                .dateTimeProvider(dateTimeProvider)
                .build();
    }

    @AfterEach
    public void tearDown()
    {
        underTest = null;
    }

    @Test
    void itShouldMakeZeroTransaction()
    {
        verifyNoMoreInteractions(transactionRepositoryMock);
    }

    @Test
    void itShouldMakeOneTransaction() {
        underTest.deposit("200");
        // verifie que la transaction a bien eu lieu
        verify(transactionRepositoryMock).save(any(Transaction.class));
    }

    @Test
    void itShouldMakeTwoTransaction() {
        underTest.deposit( "200");
        underTest.withdraw( "150");
        // verifie que la transaction a bien eu lieu
        verify(transactionRepositoryMock, times(2)).save(any(Transaction.class));
    }
}