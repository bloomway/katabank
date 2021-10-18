package com.kleematik.katabank;

import com.kleematik.katabank.application.common.DateTimeProvider;
import com.kleematik.katabank.domain.model.account.Account;
import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.domain.services.print.ConsolePrintStatement;
import com.kleematik.katabank.domain.services.print.PrintStatement;
import com.kleematik.katabank.infra.data.repository.inmemory.InMemoryTransactionRepositoryImpl;
import com.kleematik.katabank.infra.datetime.SystemDateTimeProviderImpl;

public class KataBankApplication {
	public static void main(String[] args) throws InterruptedException {

		final TransactionRepository repository = InMemoryTransactionRepositoryImpl.getInstance();
		final DateTimeProvider dateTimeProvider = SystemDateTimeProviderImpl.getInstance();
		final PrintStatement printStatement = ConsolePrintStatement.getInstance();

		final Account account = Account.builder()
				.transactionRepository(repository)
				.dateTimeProvider(dateTimeProvider)
				.printStatement(printStatement)
				.build();

		account.make(Transaction.Kind.DEPOSIT, "200");
		Thread.sleep(4000);

		account.make(Transaction.Kind.WITHDRAW, "200");
		Thread.sleep(2000);

		account.make(Transaction.Kind.WITHDRAW, "150");
		Thread.sleep(3000);

		account.make(Transaction.Kind.DEPOSIT, "200");
		Thread.sleep(1000);

		account.make(Transaction.Kind.WITHDRAW, "300");
		Thread.sleep(2000);

		account.make(Transaction.Kind.WITHDRAW, "150");

		account.printStatement();
	}

}
