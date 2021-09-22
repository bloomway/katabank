package com.kleematik.katabank.domain.model.account;

import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountTest
{
    @Mock
    private TransactionRepository transactionRepositoryMock;
    private Account underTest;

    @BeforeEach
    public void before()
    {
        underTest = Account.of();
    }

    @AfterEach
    public void tearDown()
    {
        underTest = null;
    }

    @Test
    void itShouldMakeZeroTransactionToMyAccount()
    {
        verifyNoMoreInteractions(transactionRepositoryMock);
    }

    @Test
    void itShouldMakeOneTransactionToMyAccount()
    {
        underTest.deposit("05/11/2019", "200");
        // verifie que la transaction a bien eu lieu
        verify(transactionRepositoryMock).save(any(Transaction.class));
    }

    @Test
    void itShouldMakeTwoTransactionToMyAccount()
    {
        underTest.deposit("05/11/2019", "200");
        underTest.withdraw("15/11/2019", "150");
        // verifie que la transaction a bien eu lieu
        verify(transactionRepositoryMock, times(2)).save(any(Transaction.class));
    }
}