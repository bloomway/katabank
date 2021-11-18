package com.kleematik.katabank;

import com.kleematik.katabank.application.logging.PrintStatement;
import com.kleematik.katabank.domain.model.account.Account;
import com.kleematik.katabank.domain.model.transaction.TransactionType;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.infra.di.Factory;

public class KataBankApplication {
	public static void main(String[] args) throws InterruptedException {

		final TransactionRepository repository = Factory.createTransactionRepository();
		final PrintStatement printStatement = Factory.createConsolePrintStatement();

		final Account account = Factory.createAccount(repository, printStatement);

		account.makeTransaction(TransactionType.DEPOSIT, "200");
		Thread.sleep(2000);

		account.makeTransaction(TransactionType.WITHDRAW, "150");
		Thread.sleep(2000);

		account.makeTransaction(TransactionType.WITHDRAW, "149.99");
		Thread.sleep(2000);

		account.makeTransaction(TransactionType.DEPOSIT, "199.99");
		Thread.sleep(2000);

		account.makeTransaction(TransactionType.WITHDRAW, "299.99");
		Thread.sleep(2000);

		account.makeTransaction(TransactionType.WITHDRAW, "149.99");

		account.printStatement();
	}

}
